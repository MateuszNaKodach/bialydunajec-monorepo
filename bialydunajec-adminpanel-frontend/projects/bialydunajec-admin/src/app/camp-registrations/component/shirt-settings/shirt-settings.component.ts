import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {CampEditionResponse} from '../../../camp-edition/service/rest/response/camp-edition.response';
import {CampRegistrationsEndpoint} from '../../service/rest/camp-registrations.endpoint';
import {EditFormMode} from '../../../academic-ministry/component/academic-ministry-edit/edit-form.mode';
import {FormBuilder, Validators} from '@angular/forms';
import {CampShirtEndpoint} from '../../service/rest/camp-shirt.endpoint';
import {
  CampEditionShirtDto,
  ColorDto,
  ShirtColorOptionDto,
  ShirtSizeDto,
  ShirtSizeOptionDto
} from '../../service/rest/dto/camp-edition-shirt.dto';
import {tap} from 'rxjs/operators';
import {AngularFormHelper} from '../../../shared/helper/angular-form.helper';
import {CampEditionShirtSizeRequest} from '../../service/rest/request/camp-edition-shirt-size.request';
import {CampEditionShirtColorRequest} from '../../service/rest/request/camp-edition-shirt-color.request';

@Component({
  selector: 'bda-admin-shirt-settings',
  templateUrl: './shirt-settings.component.html',
  styleUrls: ['./shirt-settings.component.less']
})
export class ShirtSettingsComponent implements OnInit {

  availableCampEditions: Observable<CampEditionResponse[]>;
  currentCampEdition: number;

  campShirt$: Observable<CampEditionShirtDto>;
  femaleSizeOptions: ShirtSizeOptionDto[] = [];
  maleSizeOptions: ShirtSizeOptionDto[] = [];
  colorOptions: ShirtColorOptionDto[] = [];

  tableIsLoading = false;

  submittingInProgress = false;

  colorModal = {
    isVisible: false,
    mode: EditFormMode.CREATE,
    currentColorId: null,
    onSubmit: (campEditionShirtId: string) => {
      const form = this.colorModal.form;
      const formValue = form.value;
      AngularFormHelper.markFormGroupDirty(form);
      if (form.valid) {
        const request = new CampEditionShirtColorRequest(
          new ColorDto(
            formValue.name,
            formValue.hexValue
          ),
          formValue.available
        );
        switch (this.colorModal.mode) {
          case EditFormMode.CREATE: {
            this.campShirtEndpoint.addCampEditionShirtColorOption(
              campEditionShirtId,
              request)
              .pipe(tap(() => this.onSubmitShirtColorOption()))
              .subscribe({error: err => console.log(err)});
            break;
          }
          case EditFormMode.EDIT: {
            this.campShirtEndpoint.updateCampEditionShirtColorOption(
              campEditionShirtId,
              this.colorModal.currentColorId,
              request)
              .pipe(tap(() => this.onSubmitShirtColorOption()))
              .subscribe({error: err => console.log(err)});
            break;
          }
        }
      }
    },
    onCancel: () => {
      this.colorModal.isVisible = false;
    },
    form: this.formBuilder.group({
      name: [null, Validators.required],
      hexValue: [null, []],
      available: [null, []]
    })
  };

  sizeModal = {
    isVisible: false,
    mode: EditFormMode.CREATE,
    currentSizeId: null,
    shirtType: null,
    onSubmit: (campEditionShirtId: string) => {
      const formValue = this.sizeModal.form.value;
      AngularFormHelper.markFormGroupDirty(this.sizeModal.form);
      const request = new CampEditionShirtSizeRequest(
        new ShirtSizeDto(
          formValue.name,
          this.sizeModal.shirtType,
          formValue.height,
          formValue.width,
          formValue.length
        ),
        formValue.available
      );
      if (this.sizeModal.form.valid && this.sizeModal.mode === EditFormMode.CREATE) {
        this.campShirtEndpoint.addCampEditionShirtSizeOption(
          campEditionShirtId,
          request
        ).pipe(
          tap(
            () => {
              this.onSubmitShirtSizeOption();
            }
          )
        ).subscribe({error: err => console.log(err)});
      } else if (this.sizeModal.form.valid && this.sizeModal.mode === EditFormMode.EDIT) {
        this.campShirtEndpoint.updateCampEditionShirtSizeOption(
          campEditionShirtId,
          this.sizeModal.currentSizeId,
          request
        ).pipe(
          tap(
            () => {
              this.onSubmitShirtSizeOption();
            }
          )
        ).subscribe({error: err => console.log(err)});
      }
    },
    onCancel: () => {
      this.sizeModal.isVisible = false;
    },
    form: this.formBuilder.group({
      name: [null, [Validators.required]],
      height: [null, [Validators.required]],
      width: [null, []],
      length: [null, []],
      available: [false, []]
    })
  };

  constructor(
    private formBuilder: FormBuilder,
    private campRegistrationsEndpoint: CampRegistrationsEndpoint,
    private campShirtEndpoint: CampShirtEndpoint
  ) {
  }

  ngOnInit() {
    this.availableCampEditions = this.campRegistrationsEndpoint.getAllCampEditions();
  }

  onCampEditionIdSelected(selectedCampEditionId: number) {
    this.currentCampEdition = selectedCampEditionId;
    this.tableIsLoading = true;
    this.updateCampShirt(this.currentCampEdition);
  }

  private updateCampShirt(selectedCampEditionId: number) {
    this.campShirt$ = this.campShirtEndpoint.getCampEditionShirt(selectedCampEditionId)
      .pipe(
        tap(shirt => {
          this.femaleSizeOptions = shirt.sizeOptions.filter(it => it.size.type === 'FEMALE')
            .sort((n1, n2) => n1.size.height - n2.size.height);
          this.maleSizeOptions = shirt.sizeOptions.filter(it => it.size.type === 'MALE')
            .sort((n1, n2) => n1.size.height - n2.size.height);
          this.colorOptions = shirt.colorOptions;
          this.tableIsLoading = false;
        })
      );
  }

  onClickAddFemaleShirtSize() {
    if (this.sizeModal.mode === EditFormMode.EDIT) {
      this.sizeModal.mode = EditFormMode.CREATE;
      this.sizeModal.form.reset();
      this.sizeModal.currentSizeId = null;
    }
    this.showShirtSizeModal('FEMALE');
  }

  onClickAddMaleShirtSize() {
    if (this.sizeModal.mode === EditFormMode.EDIT) {
      this.sizeModal.mode = EditFormMode.CREATE;
      this.sizeModal.form.reset();
      this.sizeModal.currentSizeId = null;
    }
    this.showShirtSizeModal('MALE');
  }

  showShirtSizeModal(shirtType: string) {
    this.sizeModal.shirtType = shirtType;
    this.sizeModal.isVisible = true;
  }

  onClickEditSize(data: ShirtSizeOptionDto) {
    this.sizeModal.mode = EditFormMode.EDIT;
    this.sizeModal.currentSizeId = data.shirtSizeOptionId;
    this.sizeModal.form.reset();
    this.sizeModal.form.setValue({
      name: data.size.name,
      height: data.size.height,
      width: data.size.width,
      length: data.size.length,
      available: data.available
    });
    this.sizeModal.shirtType = data.size.type;
    this.sizeModal.isVisible = true;
  }

  onClickEditColor(data: ShirtColorOptionDto) {
    this.colorModal.mode = EditFormMode.EDIT;
    this.colorModal.currentColorId = data.shirtColorOptionId;
    this.colorModal.form.reset();
    this.colorModal.form.setValue({
      name: data.color.name,
      hexValue: data.color.hexValue,
      available: data.available
    });
    this.colorModal.isVisible = true;
  }

  onClickAddShirtColor() {
    if (this.colorModal.mode === EditFormMode.EDIT) {
      this.colorModal.mode = EditFormMode.CREATE;
      this.colorModal.form.reset();
      this.colorModal.currentColorId = null;
    }
    this.colorModal.isVisible = true;
  }

  private onSubmitShirtSizeOption() {
    this.sizeModal.form.reset();
    this.sizeModal.shirtType = null;
    this.sizeModal.currentSizeId = null;
    this.sizeModal.isVisible = false;
    this.updateCampShirt(this.currentCampEdition);
  }

  private onSubmitShirtColorOption() {
    this.colorModal.form.reset();
    this.colorModal.currentColorId = null;
    this.sizeModal.isVisible = false;
    this.updateCampShirt(this.currentCampEdition);
  }

}

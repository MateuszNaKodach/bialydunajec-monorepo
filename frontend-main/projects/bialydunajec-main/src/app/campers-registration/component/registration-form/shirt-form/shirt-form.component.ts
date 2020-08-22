import {Component} from '@angular/core';
import {FormBuilder, FormControl, Validators} from '@angular/forms';
import {RegistrationFormStepAbstractComponent} from '../registration-form-step.abstract-component';
import {CamperRegistrationFormStateService} from '../../../service/camper-registration-form-state.service';
import {StepId} from '../registration-form.config';
import {Gender} from '../../../../shared/model/gender.enum';
import {CamperRegistrationFormNavigator} from '../../../service/camper-registration-form.navigator';
import {ActivatedRoute} from '@angular/router';
import {CampRegistrationsEndpoint} from '../../../service/rest/camp-registrations-endpoint.service';
import {tap} from 'rxjs/operators';
import {ShirtColorOptionDto, ShirtSizeOptionDto} from '../../../service/rest/dto/camp-edition-shirt.dto';

@Component({
  selector: 'bda-shirt-form',
  templateUrl: './shirt-form.component.html',
  styleUrls: ['./shirt-form.component.scss']
})
export class ShirtFormComponent extends RegistrationFormStepAbstractComponent {


  private availableShirtColors: ShirtColorOptionDto[] = [];
  private availableMaleShirtSizes: ShirtSizeOptionDto[] = [];
  private availableFemaleShirtSizes: ShirtSizeOptionDto[] = [];

  private _shirtSizeOptions: ShirtSizeOptionDto[] = [];


  constructor(
    mainFormState: CamperRegistrationFormStateService,
    formNavigator: CamperRegistrationFormNavigator,
    stepRoute: ActivatedRoute,
    private campRegistrationsEndpoint: CampRegistrationsEndpoint,
    private formBuilder: FormBuilder) {
    super(StepId.SHIRT, mainFormState, formNavigator, stepRoute);
  }

  startFormStepInit() {
    this.campRegistrationsEndpoint.getCampEditionShirtByInProgressCampRegistrations()
      .pipe(
        tap(shirt => {
          this.availableShirtColors = shirt.colorOptions.filter(it => it.available);
          this.availableFemaleShirtSizes = shirt.sizeOptions.filter(it => it.available && it.size.type === 'FEMALE')
            .sort((n1, n2) => n1.size.height - n2.size.height);
          this.availableMaleShirtSizes = shirt.sizeOptions.filter(it => it.available && it.size.type === 'MALE')
            .sort((n1, n2) => n1.size.height - n2.size.height);
        }),
        tap(() => this.updateFormByClothTypeValue(this.clothType.value))
      ).subscribe(r => console.log(r), e => console.log(e));
  }

  protected initStepFormControls() {
    this.stepForm = this.formBuilder.group({
        color: [null, [Validators.required]],
        clothType: [null, Validators.required],
        size: [null, Validators.required]
      }
    );
    const sub = this.stepForm.get('clothType').valueChanges
      .subscribe(value => this.updateFormByClothTypeValue(value));
    this.composeSubscription.add(sub);
  }


  private updateFormByClothTypeValue(value) {
    console.log('VALUE', value);
    this.stepForm.removeControl('size');
    if (value == 'MALE') {
      this._shirtSizeOptions = this.availableMaleShirtSizes;
      this.addSizeControl();
    } else if (value == 'FEMALE') {
      this._shirtSizeOptions = this.availableFemaleShirtSizes;
      this.addSizeControl();
    } else if (value == null) {
      this._shirtSizeOptions = [];
    }
  }

  addSizeControl() {
    const control = new FormControl(this.getStepFormDataSnapshot().size && this.getStepFormDataSnapshot().clothType == this.clothType.value
      ? this.getStepFormDataSnapshot().size : null, [Validators.required]);
    this.stepForm.addControl('size', control);
  }

  get color() {
    return this.stepForm.get('color');
  }

  get size() {
    return this.stepForm.get('size');
  }

  get clothType() {
    return this.stepForm.get('clothType');
  }

  getShirtColorOptions() {
    return this.availableShirtColors;
  }

  get shirtSizeOptions() {
    return this._shirtSizeOptions;
  }

  getClothTypeOptions() {
    return [
      {id: Gender.FEMALE, name: 'Damska'},
      {id: Gender.MALE, name: 'Męska'}
    ];
  }

}

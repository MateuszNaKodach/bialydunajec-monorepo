import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AlertViewModel} from '../../../shared/view-model/ng-zorro/alert.view-model';
import {CottageEditFormModel} from './cottage-edit.form-model';
import {EditFormMode} from '../../../academic-ministry/component/academic-ministry-edit/edit-form.mode';
import {campRegistrationsRoutingPaths} from '../../camp-registrations-routing.paths';
import {CampRegistrationsEndpoint} from '../../service/rest/camp-registrations.endpoint';
import {ActivatedRoute, Params, UrlSegment} from '@angular/router';
import {tap} from 'rxjs/operators';
import {AcademicMinistryResponse} from '../../../academic-ministry/service/rest/response/academic-ministry.response';
import {AcademicMinistryEditFormModel} from '../../../academic-ministry/component/academic-ministry-edit/academic-ministry-edit.form-model';

@Component({
  selector: 'bda-admin-cottage-edit',
  templateUrl: './cottage-edit.component.html',
  styleUrls: ['./cottage-edit.component.less']
})
export class CottageEditComponent implements OnInit, MultiModeForm<CottageEditFormModel> {

  // common
  campRegistrationsRoutingPaths = campRegistrationsRoutingPaths;

  // ui
  lastAlert: AlertViewModel;
  submittingInProgress = false;

  // logic
  private currentFormMode: EditFormMode = EditFormMode.PREVIEW;
  cottageForm: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private campRegistrationsEndpoint: CampRegistrationsEndpoint) {
  }

  ngOnInit() {
    this.initFormTemplate();

    // determine mode
    this.route.url
      .subscribe((urlSegments: UrlSegment[]) => {
          const isEdit = urlSegments
            .map(s => s.path)
            .find(p => p === campRegistrationsRoutingPaths.editCottage) != null;
          this.currentFormMode = isEdit ? EditFormMode.EDIT : EditFormMode.PREVIEW;
        }
      );

    // load init form values

  }

  determineModeFunction = (activatedRouteParams: Params) => {
    if (activatedRouteParams['cottageId'] != null) {
      return EditFormMode.EDIT;
    } else {
      return EditFormMode.CREATE;
    }
  };


  initFormTemplate() {
    this.cottageForm = this.formBuilder.group({
      cottageType: [null, []],
      academicMinistryId: [null, []],
      name: [null, []],
      logoImageUrl: [null, []],
      buildingPhotoUrl: [null, []],
      place: this.formBuilder.group({
        address: this.formBuilder.group({
          street: [null, [Validators.required]],
          homeNumber: [null, []],
          city: [null, [Validators.required]],
          postalCode: [null, []],
        }),
        geoLocation: this.formBuilder.group({
          latitude: [null, []],
          longitude: [null, []]
        }),
      }),
      cottageSpace: this.formBuilder.group({
        fullCapacity: [null, [Validators.required]],
        reservations: [null, [Validators.required]],
        maxFemaleTotal: [null, []],
        maxMaleTotal: [null, []],
        highSchoolRecentGraduatesCapacity: [null, []],
        maxFemaleHighSchoolRecentGraduates: [null, []],
        maxMaleHighSchoolRecentGraduates: [null, []],
      }),
      camperLimitations: this.formBuilder.group({
        ageRange: this.formBuilder.group({
          min: [null, []],
          max: [null, []],
        })
      }),
      bankTransferDetails: this.formBuilder.group({
        accountNumber: [null, [Validators.required]],
        accountOwner: [null, [Validators.required]],
        accountOwnerAddress: [null, []],
        transferTitleTemplate: [null, []]
      })
    });
  }

  get isPreviewMode() {
    return this.currentFormMode === EditFormMode.PREVIEW;
  }

  get isEditMode() {
    return this.currentFormMode === EditFormMode.EDIT;
  }

  get showInputs() {
    return this.currentFormMode === EditFormMode.EDIT || this.currentFormMode === EditFormMode.CREATE;
  }

  onSubmit() {

  }

}


interface MultiModeForm<FormModel> {
  submittingInProgress: boolean;
  lastAlert: AlertViewModel;
}

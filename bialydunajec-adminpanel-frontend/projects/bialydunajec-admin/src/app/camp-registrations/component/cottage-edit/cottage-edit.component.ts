import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AlertViewModel} from '../../../shared/view-model/ng-zorro/alert.view-model';
import {CottageEditFormModel} from './cottage-edit.form-model';
import {EditFormMode} from '../../../academic-ministry/component/academic-ministry-edit/edit-form.mode';
import {campRegistrationsRoutingPaths} from '../../camp-registrations-routing.paths';
import {CampRegistrationsEndpoint} from '../../service/rest/camp-registrations.endpoint';
import {ActivatedRoute, UrlSegment} from '@angular/router';
import {finalize, flatMap} from 'rxjs/operators';
import {CottageResponse} from '../../service/rest/response/cottage.response';
import {AngularFormHelper} from '../../../../../../bialydunajec-main/src/app/shared/helper/angular-form.helper';
import {UpdateCottageRequest} from '../../service/rest/request/update-cottage.request';
import {Observable} from 'rxjs';
import {AcademicMinistryResponse} from '../../service/rest/response/academic-ministry.response';
import {HttpResponseHelper} from '../../../shared/helper/HttpResponseHelper';

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
  changedStatusInProgress = false;

  // logic
  private currentFormMode: EditFormMode = EditFormMode.PREVIEW;
  cottageForm: FormGroup;

  // data
  currentCampRegistrationsId: number = 36; //TODO: Add real from path!
  currentCottageId: string;
  currentCottageStatus: string;
  currentCottageAcademicMinistryId: string;
  availableAcademicMinistries: Observable<AcademicMinistryResponse[]>;

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

    // TODO: Add campRegistrationsId to all urls - /panel/camp-registrations/{id}/cottages
    // load init form values
    this.route.params
      .pipe(
        flatMap(params => this.campRegistrationsEndpoint
          .getCottagesByIdAndCampRegistrationsEditionId(params['cottageId'], this.currentCampRegistrationsId))
      )
      .subscribe(
        (response: CottageResponse) => {
          console.log('Academic ministry:', response);
          this.currentCottageId = response.cottageId;
          this.currentCottageAcademicMinistryId = response.academicMinistryId;
          this.currentCottageStatus = response.cottageState;
          this.updateFormValuesWith(CottageEditFormModel.fromDto(response));
        }
      );
    this.availableAcademicMinistries = this.campRegistrationsEndpoint.getAllAcademicMinistries();

  }

  updateFormValuesWith(formModel: CottageEditFormModel) {
    this.cottageForm.patchValue(formModel);
    this.cottageForm.updateValueAndValidity();
  }

  initFormTemplate() {
    this.cottageForm = this.formBuilder.group({
      cottageType: [null, []],
      academicMinistryId: [null, []],
      name: [null, [Validators.required]],
      logoImageUrl: [null, []],
      buildingPhotoUrl: [null, []],
      place: this.formBuilder.group({
        address: this.formBuilder.group({
          street: [null, []],
          homeNumber: [null, []],
          city: [null, []],
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
        accountNumber: [null, []],
        accountOwner: [null, []],
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
    AngularFormHelper.markFormGroupDirty(this.cottageForm);
    if (this.cottageForm.valid && this.currentFormMode === EditFormMode.EDIT) {
      this.lastAlert = null;
      this.submittingInProgress = true;
      const updateCottageRequest: UpdateCottageRequest = {...this.cottageForm.value};
      this.campRegistrationsEndpoint.updateCottage(this.currentCottageId, updateCottageRequest)
        .pipe(
          finalize(() => this.submittingInProgress = false)
        )
        .subscribe(
          response => {
            console.log(response);
            this.lastAlert = {
              type: 'success',
              message: updateCottageRequest.name,
              description: 'Chatka została poprawnie zaktualizowana!'
            };
          },
          response => {
            console.log(response);
            const error = response.error;
            const restErrors = response.error.restErrors;
            if (HttpResponseHelper.isStatus4xx(response) && restErrors) {
              this.lastAlert = {
                type: 'error',
                message: updateCottageRequest.name,
                description: 'Chatka nie została zaktualizowana, z powodu złamania reguł:' +
                  restErrors.map((e: string) => ` ${e}`)
              };
            } else if (response.status === 0) {
              this.lastAlert = {
                type: 'error',
                message: updateCottageRequest.name,
                description: 'Chatka nie zostało zaktualizowana, z powodu braku odpowiedzi serwera.'
              };
            } else {
              this.lastAlert = {
                type: 'error',
                message: updateCottageRequest.name,
                description: `Chatka nie zostało zaktualizowana, z powodu błędu 
                  (jeśli nie wiesz co zrobić, to skontaktuj się z administratorem): \n ${error.message}`
              };
            }
          }
        );
    }
  }

  activateCottage() {
    this.changedStatusInProgress = true;
    this.campRegistrationsEndpoint.activateCottage(this.currentCottageId)
      .pipe(finalize(() => this.changedStatusInProgress = false))
      .subscribe(
        response => {
          this.lastAlert = {
            type: 'success',
            message: 'Aktywacja chatki',
            description: 'Chatka została poprawnie aktywowana! Czekaj na pierwszych zapisanych uczestników :)'
          };
          this.currentCottageStatus = 'ACTIVATED';
        },
        response => {
          console.log(response);
          const error = response.error;
          const restErrors = response.error.restErrors;
          if (HttpResponseHelper.isStatus4xx(response) && restErrors) {
            this.lastAlert = {
              type: 'error',
              message: 'Aktywacja chatki',
              description: 'Chatka nie została aktywowana, z powodu złamania reguł:' +
                restErrors.map((e: string) => ` ${e}`)
            };
          } else if (response.status === 0) {
            this.lastAlert = {
              type: 'error',
              message: 'Aktywacja chatki',
              description: 'Chatka nie została aktywowana, z powodu braku odpowiedzi serwera.'
            };
          } else {
            this.lastAlert = {
              type: 'error',
              message: 'Aktywacja chatki',
              description: `Chatka nie została aktywowana, z powodu błędu 
                  (jeśli nie wiesz co zrobić, to skontaktuj się z administratorem): \n ${error.message}`
            };
          }
        }
      );
  }

  deactivateCottage() {
    this.changedStatusInProgress = true;
    this.campRegistrationsEndpoint.deactivateCottage(this.currentCottageId)
      .pipe(finalize(() => this.changedStatusInProgress = false))
      .subscribe(
        response => {
          this.lastAlert = {
            type: 'success',
            message: 'Dezaktywacja chatki',
            description: 'Chatka została poprawnie dezaktywowana! Zmień ten stan rzeczy jak najszybciej :)'
          };
          this.currentCottageStatus = 'CONFIGURED';
        },
        response => {
          console.log(response);
          const error = response.error;
          const restErrors = response.error.restErrors;
          if (HttpResponseHelper.isStatus4xx(response) && restErrors) {
            this.lastAlert = {
              type: 'error',
              message: 'Dezaktywacja chatki',
              description: 'Chatka nie została dezaktywowana, z powodu złamania reguł:' +
                restErrors.map((e: string) => ` ${e}`)
            };
          } else if (response.status === 0) {
            this.lastAlert = {
              type: 'error',
              message: 'Aktywacja chatki',
              description: 'Chatka nie została dezaktywowana, z powodu braku odpowiedzi serwera.'
            };
          } else {
            this.lastAlert = {
              type: 'error',
              message: 'Aktywacja chatki',
              description: `Chatka nie została dezaktywowana, z powodu błędu 
                  (jeśli nie wiesz co zrobić, to skontaktuj się z administratorem): \n ${error.message}`
            };
          }
        }
      );
  }

  // FORM CONTROLS GETTERS -----------------------------------------------------------------------------------------------------------------

  get cottageTypeFormControl() {
    return this.cottageForm.get(['cottageType']);
  }

  get academicMinistryIdFormControl() {
    return this.cottageForm.get(['academicMinistryId']);
  }

  get nameFormControl() {
    return this.cottageForm.get(['name']);
  }

  get logoImageUrlFormControl() {
    return this.cottageForm.get(['logoImageUrl']);
  }

  get buildingPhotoUrlFormControl() {
    return this.cottageForm.get(['buildingPhotoUrl']);
  }

  get streetFormControl() {
    return this.cottageForm.get(['place', 'address', 'street']);
  }

  get homeNumberFormControl() {
    return this.cottageForm.get(['place', 'address', 'homeNumber']);
  }

  get cityFormControl() {
    return this.cottageForm.get(['place', 'address', 'city']);
  }

  get postalCodeFormControl() {
    return this.cottageForm.get(['place', 'address', 'postalCode']);
  }

  get webPageUrlFormControl() {
    return this.cottageForm.get(['socialMedia', 'webPageUrl']);
  }

  private getCottageSpaceFormGroupControl(controlname: string) {
    return this.cottageForm.get(['cottageSpace', controlname]);
  }

  get cottageSpaceFullCapacityFormControl() {
    return this.getCottageSpaceFormGroupControl('fullCapacity');
  }

  get cottageSpaceReservationsFormControl() {
    return this.getCottageSpaceFormGroupControl('reservations');
  }

  get cottageSpaceMaxFemaleTotalFormControl() {
    return this.getCottageSpaceFormGroupControl('maxFemaleTotal');
  }

  get cottageSpaceMaxMaleTotalFormControl() {
    return this.getCottageSpaceFormGroupControl('maxMaleTotal');
  }

  get cottageSpaceHighSchoolRecentGraduatesCapacityFormControl() {
    return this.getCottageSpaceFormGroupControl('highSchoolRecentGraduatesCapacity');
  }

  get cottageSpaceMaxFemaleHighSchoolRecentGraduatesFormControl() {
    return this.getCottageSpaceFormGroupControl('maxFemaleHighSchoolRecentGraduates');
  }

  get cottageSpaceMaxMaleHighSchoolRecentGraduatesFormControl() {
    return this.getCottageSpaceFormGroupControl('maxMaleHighSchoolRecentGraduates');
  }

  get camperLimitationsAgeRangeMinFormControl() {
    return this.cottageForm.get(['camperLimitations', 'ageRange', 'min']);
  }

  get camperLimitationsAgeRangeMaxFormControl() {
    return this.cottageForm.get(['camperLimitations', 'ageRange', 'max']);
  }

  private getBankTransferDetailsFormGroupControl(controlname: string) {
    return this.cottageForm.get(['bankTransferDetails', controlname]);
  }

  get bankTransferDetailsAccountNumberFormControl() {
    return this.getBankTransferDetailsFormGroupControl('accountNumber');
  }

  get bankTransferDetailsAccountOwnerFormControl() {
    return this.getBankTransferDetailsFormGroupControl('accountOwner');
  }

  get bankTransferDetailsAccountOwnerAddressFormControl() {
    return this.getBankTransferDetailsFormGroupControl('accountOwnerAddress');
  }

  get bankTransferDetailsTransferTitleTemplateFormControl() {
    return this.getBankTransferDetailsFormGroupControl('transferTitleTemplate');
  }
}


interface MultiModeForm<FormModel> {
  submittingInProgress: boolean;
  lastAlert: AlertViewModel;
}

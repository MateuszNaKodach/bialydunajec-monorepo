import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AcademicMinistryEndpoint} from '../../service/rest/academic-ministry.endpoint';
import {AngularFormHelper} from '../../../../../../bialydunajec-main/src/app/shared/helper/angular-form.helper';
import {HttpResponseHelper} from '../../../shared/helper/HttpResponseHelper';
import {filter, finalize, flatMap, tap} from 'rxjs/operators';
import {CreateAcademicMinistryRequest, UpdateAcademicMinistryRequest} from '../../service/rest/request/create-academic-ministry.request';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {AcademicMinistryResponse} from '../../service/rest/response/academic-ministry.response';
import {AlertViewModel} from '../../../shared/view-model/ng-zorro/alert.view-model';
import {EditFormMode} from './edit-form.mode';
import {Observable, Observer} from 'rxjs';
import {AcademicMinistryEditFormModel} from './academic-ministry-edit.form-model';

@Component({
  selector: 'bda-admin-academic-ministry-edit',
  templateUrl: './academic-ministry-edit.component.html',
  styleUrls: ['./academic-ministry-edit.component.less']
})
export class AcademicMinistryEditComponent implements OnInit {

  currentAcademicMinistryId: string;
  formMode: EditFormMode = EditFormMode.CREATE;
  lastAlert: AlertViewModel;
  submittingInProgress = false;
  academicMinistryForm: FormGroup;
  // private formConfig = new Map<EditFormMode, {submitFn: () => Observable<any>, submitObserver: Observer<any>}>();
  private formSubmitFn;
  private formSubmitObservers;
  private determineModeFn;

  constructor(
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private academicMinistryEndpoint: AcademicMinistryEndpoint) {
  }

  initFormSubmitFunctions(createSubmitFn: (...any) => Observable<any>, editSubmitFn: (...any) => Observable<any>) {
    this.formSubmitFn = {};
    this.formSubmitFn[EditFormMode.CREATE] = createSubmitFn;
    this.formSubmitFn[EditFormMode.EDIT] = editSubmitFn;
  }

  initFormSubmitObservers(createSubmitObserver: Observer<any>, editSubmitObserver: Observer<any>) {
    this.formSubmitObservers[EditFormMode.CREATE] = createSubmitObserver;
    this.formSubmitObservers[EditFormMode.EDIT] = editSubmitObserver;
  }

  initDetermineModeFunction() {
    this.determineModeFn = (activatedRouteParams: Params) => {
      if (activatedRouteParams['academicMinistryId'] != null) {
        return EditFormMode.EDIT;
      } else {
        return EditFormMode.CREATE;
      }
    };
  }

  loadInitFormValues() {
    this.route.params
      .pipe(
        tap(params => this.updateFormMode(params)),
        flatMap(params => this.academicMinistryEndpoint.getAcademicMinistryById(params['academicMinistryId']))
      )
      .subscribe(
        (response: AcademicMinistryResponse) => {
          console.log('Academic ministry:', response);
          this.currentAcademicMinistryId = response.academicMinistryId;
          this.academicMinistryForm.patchValue(AcademicMinistryEditFormModel.fromDto(response));
          this.academicMinistryForm.updateValueAndValidity();
        }
      );
  }

  private updateFormMode(params) {
    return this.formMode = this.determineModeFn(params);
  }

  ngOnInit() {
    this.initDetermineModeFunction();
    const submitPipeOperators = [finalize(() => this.submittingInProgress = false)];
    this.initFormSubmitFunctions(
      (createAcademicMinistryRequest: CreateAcademicMinistryRequest) =>
        this.academicMinistryEndpoint.createAcademicMinistry(createAcademicMinistryRequest)
          .pipe(...submitPipeOperators, tap(() => this.academicMinistryForm.reset())),
      (academicMinistryId: string, updateAcademicMinistryRequest: UpdateAcademicMinistryRequest) =>
        this.academicMinistryEndpoint.updateAcademicMinistryById(academicMinistryId, updateAcademicMinistryRequest)
          .pipe(...submitPipeOperators)
    );
    this.initForm();
    this.loadInitFormValues();
  }

  initForm() {
    this.academicMinistryForm = this.formBuilder.group({
      officialName: [null, [Validators.required]],
      shortName: [null, []],
      logoImageUrl: [null, []],
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
      socialMedia: this.formBuilder.group({
        webPageUrl: [null, []],
        facebookPageUrl: [null, []],
        facebookGroupUrl: [null, []],
        instagramUrl: [null, []],
        youTubeChannelUrl: [null, []],
      }),
      emailAddress: [null, []],
      photoUrl: [null, []],
      description: this.formBuilder.group({
        title: [null, []],
        content: [null, [Validators.required]]
      })
    });
  }

  onSubmit() {
    AngularFormHelper.markFormGroupDirty(this.academicMinistryForm);
    if (this.academicMinistryForm.valid) {
      this.lastAlert = null;
      this.submittingInProgress = true;
      if (this.isEditMode) {
        const updateAcademicMinistryRequest: UpdateAcademicMinistryRequest = {...this.academicMinistryForm.value};
        this.formSubmitFn[this.formMode](this.currentAcademicMinistryId, updateAcademicMinistryRequest)
          .subscribe(
            response => {
              console.log(response);
              this.lastAlert = {
                type: 'success',
                message: updateAcademicMinistryRequest.officialName,
                description: 'Duszpasterstwo zostało poprawnie zaktualizowane!'
              };
            },
            response => {
              console.log(response);
              const error = response.error;
              const restErrors = response.error.restErrors;
              if (HttpResponseHelper.isStatus4xx(response) && restErrors) {
                this.lastAlert = {
                  type: 'error',
                  message: updateAcademicMinistryRequest.officialName,
                  description: 'Duszpasterstwo nie zostało zaktualizowane, z powodu złamania reguł:' +
                    restErrors.map((e: string) => ` ${e}`)
                };
              } else if (response.status === 0) {
                this.lastAlert = {
                  type: 'error',
                  message: updateAcademicMinistryRequest.officialName,
                  description: 'nie zostało zaktualizowane, z powodu braku odpowiedzi serwera.'
                };
              } else {
                this.lastAlert = {
                  type: 'error',
                  message: updateAcademicMinistryRequest.officialName,
                  description: `Duszpasterstwo nie zostało zaktualizowane, z powodu błędu 
                  (jeśli nie wiesz co zrobić, to skontaktuj się z administratorem): \n ${error.message}`
                };
              }
            }
          );
      } else {
        const createAcademicMinistryRequest: CreateAcademicMinistryRequest = {...this.academicMinistryForm.value};
        this.formSubmitFn[this.formMode](createAcademicMinistryRequest)
          .subscribe(
            response => {
              console.log('Create academic Ministry response:', response);
              //this.academicMinistryForm.reset();
              this.lastAlert = {
                type: 'success',
                message: createAcademicMinistryRequest.officialName,
                description: 'Duszpasterstwo zostało poprawnie utworzone!'
              };
            },
            response => {
              console.log(response);
              const error = response.error;
              const restErrors = response.error.restErrors;
              if (HttpResponseHelper.isStatus4xx(response) && restErrors) {
                this.lastAlert = {
                  type: 'error',
                  message: createAcademicMinistryRequest.officialName,
                  description: 'Duszpasterstwo nie zostało utworzone, z powodu złamania reguł:' +
                    restErrors.map((e: string) => ` ${e}`)
                };
              } else if (response.status === 0) {
                this.lastAlert = {
                  type: 'error',
                  message: createAcademicMinistryRequest.officialName,
                  description: 'Duszpasterstwo nie zostało utworzone, z powodu braku odpowiedzi serwera.'
                };
              } else {
                this.lastAlert = {
                  type: 'error',
                  message: createAcademicMinistryRequest.officialName,
                  description: `Duszpasterstwo nie zostało utworzone, z powodu błędu 
                  (jeśli nie wiesz co zrobić, to skontaktuj się z administratorem): \n ${error.message}`
                };
              }
            }
          );
      }
    }
  }

  get isEditMode() {
    return this.formMode === EditFormMode.EDIT;
  }

  get isCreateMode() {
    return this.formMode === EditFormMode.EDIT;
  }

  get officialNameFormControl() {
    return this.academicMinistryForm.get('officialName');
  }

  get shortNameFormControl() {
    return this.academicMinistryForm.get('shortName');
  }

  get logoImageUrlFormControl() {
    return this.academicMinistryForm.get('logoImageUrl');
  }

  get streetFormControl() {
    return this.academicMinistryForm.get(['place', 'address', 'street']);
  }

  get homeNumberFormControl() {
    return this.academicMinistryForm.get(['place', 'address', 'homeNumber']);
  }

  get cityFormControl() {
    return this.academicMinistryForm.get(['place', 'address', 'city']);
  }

  get postalCodeFormControl() {
    return this.academicMinistryForm.get(['place', 'address', 'postalCode']);
  }

  get webPageUrlFormControl() {
    return this.academicMinistryForm.get(['socialMedia', 'webPageUrl']);
  }

  get facebookPageUrlFormControl() {
    return this.academicMinistryForm.get(['socialMedia', 'facebookPageUrl']);
  }

  get facebookGroupUrlFormControl() {
    return this.academicMinistryForm.get(['socialMedia', 'facebookGroupUrl']);
  }

  get instagramUrlFormControl() {
    return this.academicMinistryForm.get(['socialMedia', 'instagramUrl']);
  }

  get youTubeChannelUrlFormControl() {
    return this.academicMinistryForm.get(['socialMedia', 'youTubeChannelUrl']);
  }

  get emailAddressFormControl() {
    return this.academicMinistryForm.get(['socialMedia', 'emailAddress']);
  }

  get photoUrlFormControl() {
    return this.academicMinistryForm.get(['socialMedia', 'photoUrl']);
  }

  get descriptionTitleFormControl() {
    return this.academicMinistryForm.get(['description', 'title']);
  }

  get descriptionContentFormControl() {
    return this.academicMinistryForm.get(['description', 'content']);
  }
}

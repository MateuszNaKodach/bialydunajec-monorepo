import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AcademicMinistryEndpoint} from '../../service/rest/academic-ministry.endpoint';
import {AngularFormHelper} from '../../../../../../bialydunajec-main/src/app/shared/helper/angular-form.helper';
import {HttpResponseHelper} from '../../../shared/helper/HttpResponseHelper';
import {filter, finalize, flatMap, tap} from 'rxjs/operators';
import {CreateAcademicMinistryRequest} from '../../service/rest/request/create-academic-ministry.request';
import {ActivatedRoute} from '@angular/router';
import {AcademicMinistryResponse} from '../../service/rest/response/academic-ministry.response';
import {AlertViewModel} from '../../../shared/view-model/ng-zorro/alert.view-model';
import {EditFormMode} from './edit-form.mode';

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

  constructor(
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private academicMinistryEndpoint: AcademicMinistryEndpoint) {
  }

  ngOnInit() {
    this.initForm();
    this.route.params
      .pipe(
        filter(params => params['academicMinistryId'] != null),
        tap(_ => this.formMode = EditFormMode.EDIT),
        flatMap(params => this.academicMinistryEndpoint.getAcademicMinistryById(params['academicMinistryId']))
      )
      .subscribe(
        (response: AcademicMinistryResponse) => {
          console.log('Academic ministry:', response);
          this.currentAcademicMinistryId = response.academicMinistryId;
          this.academicMinistryForm.patchValue({...response});
          this.academicMinistryForm.updateValueAndValidity();
        }
      );
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
        content: [null, []]
      })
    });
  }

  onSubmit() {
    AngularFormHelper.markFormGroupDirty(this.academicMinistryForm);
    if (this.academicMinistryForm.valid) {
      this.lastAlert = null;
      this.submittingInProgress = true;
      const createAcademicMinistryRequest: CreateAcademicMinistryRequest = {...this.academicMinistryForm.value};
      if (this.isEditMode) {
        this.academicMinistryEndpoint.updateAcademicMinistryById(this.currentAcademicMinistryId, this.academicMinistryForm.value)
          .pipe(
            finalize(() => this.submittingInProgress = false)
          )
          .subscribe(
            response => {
              console.log(response);
              this.lastAlert = {
                type: 'success',
                message: createAcademicMinistryRequest.officialName,
                description: 'zostało poprawnie zaktualizowane!'
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
                  description: 'nie zostało zaktualizowane, z powodu złamania reguł:' +
                    restErrors.map((e: string) => ` ${e}`)
                };
              } else if (response.status === 0) {
                this.lastAlert = {
                  type: 'error',
                  message: createAcademicMinistryRequest.officialName,
                  description: 'nie zostało zaktualizowane, z powodu braku odpowiedzi serwera.'
                };
              } else {
                this.lastAlert = {
                  type: 'error',
                  message: createAcademicMinistryRequest.officialName,
                  description: `nie zostało zaktualizowane, z powodu błędu (jeśli nie wiesz co zrobić, to skontaktuj się z administratorem): \n ${error.message}`
                };
              }
            }
          );
      } else {
        this.academicMinistryEndpoint.createAcademicMinistry(this.academicMinistryForm.value)
          .pipe(
            finalize(() => this.submittingInProgress = false)
          )
          .subscribe(
            response => {
              console.log(response);
              this.academicMinistryForm.reset();
              this.lastAlert = {
                type: 'success',
                message: createAcademicMinistryRequest.officialName,
                description: 'zostało poprawnie utworzone!'
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
                  description: 'nie zostało utworzone, z powodu złamania reguł:' +
                    restErrors.map((e: string) => ` ${e}`)
                };
              } else if (response.status === 0) {
                this.lastAlert = {
                  type: 'error',
                  message: createAcademicMinistryRequest.officialName,
                  description: 'nie zostało utworzone, z powodu braku odpowiedzi serwera.'
                };
              } else {
                this.lastAlert = {
                  type: 'error',
                  message: createAcademicMinistryRequest.officialName,
                  description: `nie zostało utworzone, z powodu błędu (jeśli nie wiesz co zrobić, to skontaktuj się z administratorem): \n ${error.message}`
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

import {Injectable} from '@angular/core';
import {StepId} from '../component/registration-form/registration-form.config';
import {FormStatus} from '../model/form-status.enum';
import {Subject} from 'rxjs';

@Injectable()
export class CamperRegistrationFormStateService {

  private formStateSubject = new Subject<{ stepId: StepId, formState: any }>();

  private formState = new Map<string, any>([
    [
      StepId.PERSONAL_DATA,
      {
        status: FormStatus.UNKNOWN,
        submitted: false,
        data: {
          personalData: {
            gender: null,
            firstName: 'Mateusz',
            lastName: null,
            pesel: null
          },
          homeAddress: {
            street: null,
            number: null,
            postalCode: null,
            city: null
          },
          contact: {
            email: null,
            telephone: null,
          },
          education: {
            university: null,
            faculty: null,
            fieldOfStudy: null,
            isRecentHighSchoolGraduate: false,
            highSchool: null
          },
          statistics: {
            knowAboutCampFrom: null,
            wasCamperInThePast: null,
            onCampForTime: null
          }
        }
      }
    ],
    [
      StepId.TRANSPORT,
      {
        status: FormStatus.UNKNOWN,
        submitted: false,
        data: {
          testControl: null
        }
      }
    ]
  ]);

  constructor() {
  }

  get formStateChanges() {
    return this.formStateSubject.asObservable();
  }

  getPersonalFormDataSnapshot() {
    return {...this.formState.get(StepId.PERSONAL_DATA).data};
  }

  savePersonalFormData(formData: any) {
    this.formState.get(StepId.PERSONAL_DATA).data = formData;
    this.publishFormStateChange(StepId.PERSONAL_DATA, this.formState.get(StepId.PERSONAL_DATA));
  }

  getTransportFormDataSnapshot() {
    return {...this.formState.get(StepId.TRANSPORT).data};
  }

  saveTransportFormData(formState: any) {
    this.formState.get(StepId.TRANSPORT).data = formState;
    this.publishFormStateChange(StepId.TRANSPORT, this.formState.get(StepId.TRANSPORT));
  }

  updateFormStatus(stepId: StepId, status: FormStatus) {
    this.formState.get(stepId).status = status;
    this.publishFormStateChange(stepId, status);
  }

  private publishFormStateChange(stepId: StepId, formState: any) {
    console.log(this.formState);
    this.formStateSubject.next({stepId, formState});
  }


}

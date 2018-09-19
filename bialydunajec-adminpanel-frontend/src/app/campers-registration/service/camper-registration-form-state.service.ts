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
        // submitted: false,
        data: {
          personalData: {
            gender: null,
            firstName: null,
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
            wasCamperInThePast: false,
            onCampForTime: null
          }
        }
      }
    ],
    [
      StepId.TRANSPORT,
      {
        status: FormStatus.UNKNOWN,
        // submitted: false,
        data: {
          meanOfTransport: null,
          campTransport: {
            destinationBusStop: null,
          }
        }
      }
    ],
    [
      StepId.SHIRT,
      {
        status: FormStatus.UNKNOWN,
        // submitted: false,
        data: {
          color: null,
          size: null,
          clothType: null
        }
      }
    ],
    [
      StepId.COTTAGE,
      {
        status: FormStatus.UNKNOWN,
        // submitted: false,
        data: {}
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
    this.publishStepFormStateChange(StepId.PERSONAL_DATA, this.formState.get(StepId.PERSONAL_DATA));
  }

  getTransportFormDataSnapshot() {
    return {...this.formState.get(StepId.TRANSPORT).data};
  }

  saveTransportFormData(formState: any) {
    this.formState.get(StepId.TRANSPORT).data = formState;
    this.publishStepFormStateChange(StepId.TRANSPORT, this.formState.get(StepId.TRANSPORT));
  }

  getStepFormDataSnapshot(stepId: StepId) {
    return {...this.formState.get(stepId).data};
  }

  saveStepFormData(stepId: StepId, formState: any) {
    this.formState.get(stepId).data = formState;
    this.publishStepFormStateChange(stepId, this.formState.get(stepId));
  }

  updateStepFormStatus(stepId: StepId, status: FormStatus) {
    this.formState.get(stepId).status = status;
    this.publishStepFormStateChange(stepId, status);
  }

  getStepFormStatus(stepId: StepId) {
    return this.formState.get(stepId).status;
  }

  private publishStepFormStateChange(stepId: StepId, formState: any) {
    console.log(this.formState);
    this.formStateSubject.next({stepId, formState});
  }


}

import {Injectable} from '@angular/core';
import {StepId} from '../component/registration-form/registration-form.config';
import {campersRegistrationRoutingPaths} from '../campers-registration-routing.paths';
import {FormStepConfig} from './campers-registration.service';

@Injectable()
export class CamperRegistrationFormStateService {

  private formState = new Map<string, any>([
    [
      StepId.PERSONAL_DATA,
      {
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
          wasCamperInThePast: null,
          onCampForTime: null
        }
      }
    ],
    [
      StepId.TRANSPORT,
      {testControl: null}
    ]
  ]);

  constructor() {
  }

  getPersonalFormState() {
    return {...this.formState.get(StepId.PERSONAL_DATA)};
  }

  savePersonalFormState(formState: any) {
    this.formState.set(StepId.PERSONAL_DATA, formState);
  }

  getTransportFormState() {
    return {...this.formState.get(StepId.TRANSPORT)};
  }

  saveTransportFormState(formState: any) {
    this.formState.set(StepId.TRANSPORT, formState);
  }

  getSubmittedFormState() {
    return {
      personal: this.getPersonalFormState(),
      transport: this.getTransportFormState()
    };
  }
}

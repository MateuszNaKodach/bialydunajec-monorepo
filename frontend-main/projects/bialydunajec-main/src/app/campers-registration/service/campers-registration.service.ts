import {Injectable} from '@angular/core';
import {StepId} from '../component/registration-form/registration-form.config';
import {campersRegistrationRoutingPaths} from '../campers-registration-routing.paths';

@Injectable({
  providedIn: 'root'
})
export class CampersRegistrationService {

  stepsState = new Map<string, any>([
    [
      StepId.PERSONAL_DATA,
      {
        completed: false,
        formData: null
      }
    ],
    [
      StepId.COTTAGE,
      {
        completed: false,
        formData: null
      }
    ]
  ]);



  constructor() {
  }

  getCurrentStep() {

  }
}

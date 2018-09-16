import {Injectable} from '@angular/core';
import {StepId} from '../component/registration-form/registration-form.config';

@Injectable({
  providedIn: 'root'
})
export class CampersRegistrationService {

  stepsState = new Map<string, any>([
    [
      StepId.PERSONAL_DATA,
      {completed: false, selected: false, disabled: false}
    ],
    [
      StepId.COTTAGE,
      'Big Oranges'
    ]
  ]);


  formSteps = new Map<string, any>([
    [
      StepId.COTTAGE,
      {
        title: 'Chatka',
        description: 'Wybierz swoją chatkę',
        icon: 'house',
        relativeToFormPath: 'chatka',
        required: true
      }
    ]
  ]);


  constructor() {
  }
}

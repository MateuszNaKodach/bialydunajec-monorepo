import {Component, OnInit} from '@angular/core';
import {RegistrationStepViewModel} from '../registration-stepper/registration-step.view-model';
import {CamperRegistrationFormStateService} from '../../service/camper-registration-form-state.service';
import {campersRegistrationRoutingPaths} from '../../campers-registration-routing.paths';
import {StepId} from './registration-form.config';

@Component({
  selector: 'bda-registration-form',
  templateUrl: './registration-form.component.html',
  styleUrls: ['./registration-form.component.scss']
})
export class RegistrationFormComponent implements OnInit {

  private registrationSteps = [
    new RegistrationStepViewModel(StepId.PERSONAL_DATA, 'Dane osobowe', 'clipboard list', 'Wprowadź swoje dane', campersRegistrationRoutingPaths.personalData),
    new RegistrationStepViewModel(StepId.TRANSPORT, 'Dojazd', 'bus', 'Wybierz transport', campersRegistrationRoutingPaths.transport),
    new RegistrationStepViewModel(StepId.SHIRT, 'Koszulka', 'child', 'Wybierz kolor i rozmiar', campersRegistrationRoutingPaths.shirt),
    new RegistrationStepViewModel(StepId.COTTAGE, 'Chatka', 'home', 'Wybierz swoją chatkę', campersRegistrationRoutingPaths.cottage)
  ];

  formData;

  constructor(private formDataService: CamperRegistrationFormStateService) {
  }

  ngOnInit() {
    this.formData = this.formDataService.getSubmittedFormState();
    this.registrationSteps[0].markAsCompleted();
  }

  getRegistrationSteps() {
    return this.registrationSteps;
  }

}

import {Component, OnInit} from '@angular/core';
import {RegistrationStepViewModel} from '../registration-stepper/registration-step.view-model';

@Component({
  selector: 'bda-registration-form',
  templateUrl: './registration-form.component.html',
  styleUrls: ['./registration-form.component.scss']
})
export class RegistrationFormComponent implements OnInit {

  private registrationSteps = [
    new RegistrationStepViewModel('Dane osobowe', 'clipboard list', 'Wprowadź swoje dane'),
    new RegistrationStepViewModel('Dojazd', 'bus', 'Wybierz transport'),
    new RegistrationStepViewModel('Koszulka', 'child', 'Wybierz kolor i rozmiar'),
    new RegistrationStepViewModel('Chatka', 'warehouse', 'Wybierz swoją chatkę')
  ];

  constructor() {
  }

  ngOnInit() {
    this.registrationSteps[0].markAsCompleted();
    this.registrationSteps[1].select();
  }

  getRegistrationSteps() {
    return this.registrationSteps;
  }

}

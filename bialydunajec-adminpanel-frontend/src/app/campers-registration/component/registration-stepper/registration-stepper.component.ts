import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {RegistrationStepViewModel} from './registration-step.view-model';
import {CamperRegistrationFormNavigator} from '../../service/camper-registration-form.navigator';
import {StepId} from '../registration-form/registration-form.config';

@Component({
  selector: 'bda-registration-stepper',
  templateUrl: './registration-stepper.component.html',
  styleUrls: ['./registration-stepper.component.scss']
})
export class RegistrationStepperComponent implements OnInit {

  @Input() steps: RegistrationStepViewModel[];
  @Output() stepClicked = new EventEmitter<RegistrationStepViewModel>();

  constructor(private formNavigator: CamperRegistrationFormNavigator) {
  }

  ngOnInit() {
  }

  onStepClicked(step: RegistrationStepViewModel) {
    this.stepClicked.emit(step);
  }

  isCurrentStep(stepId: StepId) {
    return this.getCurrentStepId() === stepId;
  }

  private getCurrentStepId() {
    return this.formNavigator.getCurrentStepId();
  }


}

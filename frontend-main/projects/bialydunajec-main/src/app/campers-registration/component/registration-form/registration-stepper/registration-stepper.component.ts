import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {RegistrationStepViewModel} from './registration-step.view-model';
import {CamperRegistrationFormNavigator} from '../../../service/camper-registration-form.navigator';
import {StepId} from '../registration-form.config';
import {CamperRegistrationFormStateService} from '../../../service/camper-registration-form-state.service';
import {FormStatus} from '../../../../shared/model/form-status.enum';

@Component({
  selector: 'bda-registration-stepper',
  templateUrl: './registration-stepper.component.html',
  styleUrls: ['./registration-stepper.component.scss']
})
export class RegistrationStepperComponent implements OnInit {

  @Input() steps: RegistrationStepViewModel[];
  @Output() stepClicked = new EventEmitter<RegistrationStepViewModel>();

  constructor(
    private formNavigator: CamperRegistrationFormNavigator,
    private formStateService: CamperRegistrationFormStateService
  ) {
  }

  ngOnInit() {
  }

  onStepClicked(step: RegistrationStepViewModel) {
    this.stepClicked.emit(step);
    if (!this.isCurrentStep(step.getStepId())) {
      this.formNavigator.onFormStepperClicked(step.getStepId());
    }
  }

  isCurrentStep(stepId: StepId) {
    return stepId && this.formNavigator.isCurrentStep(stepId);
  }

  isStepInvalid(stepId: StepId) {
    return stepId && this.formStateService.getStepFormStatus(stepId) === FormStatus.INVALID;
  }

  isStepValid(stepId: StepId) {
    return stepId && this.formStateService.getStepFormStatus(stepId) === FormStatus.VALID;
  }

  private getCurrentStepId() {
    return this.formNavigator.getCurrentStepId();
  }


}

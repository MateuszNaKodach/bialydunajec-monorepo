import {Injectable} from '@angular/core';
import {RegistrationFormConfig} from '../component/registration-form/registration-form.config';
import {ActivatedRoute, Router} from '@angular/router';

@Injectable()
export class CamperRegistrationFormNavigator {

  constructor(private router: Router) {
  }

  getCurrentStepId() {
    return RegistrationFormConfig.STEPS_CONFIG
      .find(step => this.router.url.indexOf(step.relativeToFormPath) !== -1)
      .stepId;
  }

  navigateToNextStep(activatedRoute: ActivatedRoute) {
    const currentStepId = this.getCurrentStepId();
    const currentStepOrder = RegistrationFormConfig.STEPS_ORDER.indexOf(currentStepId);
    const isLastStep = currentStepOrder === RegistrationFormConfig.getLastStepOrder();
    if (!isLastStep) {
      const nextStep = RegistrationFormConfig.getStepConfigById(RegistrationFormConfig.STEPS_ORDER[currentStepOrder + 1]);
      this.router.navigate(['../' + nextStep.relativeToFormPath], {relativeTo: activatedRoute});
    }
  }

  navigateToPreviousStep(activatedRoute: ActivatedRoute) {
    const currentStepId = this.getCurrentStepId();
    const currentStepOrder = RegistrationFormConfig.STEPS_ORDER.indexOf(currentStepId);
    const isFirstStep = currentStepOrder === RegistrationFormConfig.getFirstStepOrder();
    if (!isFirstStep) {
      const previousStep = RegistrationFormConfig.getStepConfigById(RegistrationFormConfig.STEPS_ORDER[currentStepOrder - 1]);
      this.router.navigate(['../' + previousStep.relativeToFormPath], {relativeTo: activatedRoute});
    }
  }

}



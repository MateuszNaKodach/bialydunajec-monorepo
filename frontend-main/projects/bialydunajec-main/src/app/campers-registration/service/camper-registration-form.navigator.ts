import {Injectable} from '@angular/core';
import {RegistrationFormConfig, StepId} from '../component/registration-form/registration-form.config';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable, Subject} from 'rxjs';

@Injectable() //TODO: Add guards for navigation routes
export class CamperRegistrationFormNavigator {

  private formStepperClickedSubject = new Subject<StepId>();

  constructor(private router: Router) {
  }

  onFormStepperClicked(stepId: StepId) {
    this.formStepperClickedSubject.next(stepId);
  }

  get stepperClicks(): Observable<StepId> {
    return this.formStepperClickedSubject.asObservable();
  }

  getCurrentStepId(): StepId | null {
    const currentStep = RegistrationFormConfig.STEPS_CONFIG
      .find(step => this.router.url.indexOf(step.relativeToFormPath) !== -1);
    return currentStep ? currentStep.stepId : null;
  }

  isCurrentStep(stepId: StepId) {
    const currentStep = this.getCurrentStepId();
    return stepId && currentStep && currentStep === stepId;
  }

  navigateToNextStep(activatedRoute: ActivatedRoute) {
    const currentStepId = this.getCurrentStepId();
    const currentStepOrder = RegistrationFormConfig.STEPS_ORDER.indexOf(currentStepId);
    const isLastStep = currentStepOrder === RegistrationFormConfig.getLastStepOrder();
    if (!isLastStep) {
      const nextStep = this.getNextStep(currentStepOrder);
      this.router.navigate(['../' + nextStep.relativeToFormPath], {relativeTo: activatedRoute});
    }
  }

  getNextStep(currentStepOrder) {
    return RegistrationFormConfig.getStepConfigById(RegistrationFormConfig.STEPS_ORDER[currentStepOrder + 1]);
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



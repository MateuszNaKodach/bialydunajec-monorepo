import {FormGroup} from '@angular/forms';
import {StepId} from './registration-form.config';
import {FormStatus} from '../../../shared/model/form-status.enum';
import {CamperRegistrationFormStateService} from '../../service/camper-registration-form-state.service';
import {OnDestroy, OnInit} from '@angular/core';
import {CamperRegistrationFormNavigator} from '../../service/camper-registration-form.navigator';
import {ActivatedRoute} from '@angular/router';
import {AngularFormHelper} from '../../../shared/helper/angular-form.helper';
import {Subscription} from 'rxjs';

export abstract class RegistrationFormStepAbstractComponent implements OnInit, OnDestroy {

  stepForm: FormGroup;
  private stepperSubscription: Subscription;

  protected constructor(
    protected stepId: StepId,
    protected mainFormState: CamperRegistrationFormStateService,
    protected formNavigator: CamperRegistrationFormNavigator,
    protected stepRoute: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    this.initStepFormControls();
    this.loadStepDataFromSnapshot();
    this.onStepFormDataLoaded();
    if (this.getMainFormStepStatus() === FormStatus.INVALID) {
      AngularFormHelper.markFormGroupTouched(this.stepForm);
    }
    this.stepperSubscription = this.formNavigator.stepperClicks
      .subscribe(stepClicked => this.onSubmitStepForm());
  }

  protected updateMainFormStepStatus() {
    this.mainFormState.updateStepFormStatus(this.stepId, FormStatus[this.stepForm.status]);
  }

  protected getMainFormStepStatus() {
    return this.mainFormState.getStepFormStatus(this.stepId);
  }

  isStepValid() {
    return !this.stepForm.invalid;
  }

  onSubmitStepForm() {
    AngularFormHelper.markFormGroupTouched(this.stepForm);
    this.updateMainFormStepStatus();
    this.saveStepFormData();
    if (this.isStepValid()) {
      this.formNavigator.navigateToNextStep(this.stepRoute);
    }
  }

  onNavigateToPreviousStep() {
    this.formNavigator.navigateToPreviousStep(this.stepRoute);
  }

  protected getStepFormDataSnapshot() {
    return this.mainFormState.getStepFormDataSnapshot(this.stepId);
  }

  protected saveStepFormData() {
    return this.mainFormState.saveStepFormData(this.stepId, this.stepForm.value);
  }

  private loadStepDataFromSnapshot() {
    const dataSnapshot = this.getStepFormDataSnapshot();
    Object.keys(this.stepForm.controls)
      .forEach(controlName => this.stepForm.get(controlName).setValue(dataSnapshot[controlName]));
  }

  protected abstract initStepFormControls();

  protected onStepFormDataLoaded() {}

  ngOnDestroy(): void {
    this.stepperSubscription.unsubscribe();
  }

}

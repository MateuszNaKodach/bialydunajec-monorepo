import {FormGroup} from '@angular/forms';
import {StepId} from './registration-form.config';
import {FormStatus} from '../../model/form-status.enum';
import {CamperRegistrationFormStateService} from '../../service/camper-registration-form-state.service';
import {OnInit} from '@angular/core';
import {CamperRegistrationFormNavigator} from '../../service/camper-registration-form.navigator';
import {ActivatedRoute} from '@angular/router';
import {AngularFormHelper} from '../../../shared/helper/angular-form.helper';
import {Subscription} from 'rxjs';

export abstract class RegistrationFormStepAbstractComponent implements OnInit {

  stepForm: FormGroup;
  private stepperSubscription: Subscription;

  protected constructor(
    protected stepId: StepId,
    protected mainFormState: CamperRegistrationFormStateService,
    protected formNavigator: CamperRegistrationFormNavigator,
    protected stepRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.initStepFormControls();
    this.loadStepDataFromSnapshot();
    if (this.getMainFormStepStatus() === FormStatus.INVALID) {
      AngularFormHelper.markFormGroupTouched(this.stepForm);
    }
    this.stepperSubscription = this.formNavigator.stepperClicks
      .subscribe(stepClicked => this.onSubmitStepForm());
  }

  protected updateMainFormStepStatus() {
    this.mainFormState.updateFormStatus(this.stepId, FormStatus[this.stepForm.status]);
  }

  protected getMainFormStepStatus() {
    return this.mainFormState.getFormStatus(this.stepId);
  }

  isStepValid() {
    return !this.stepForm.invalid;
  }

  protected onSubmitStepForm() {
    AngularFormHelper.markFormGroupTouched(this.stepForm);
    this.updateMainFormStepStatus();
    this.saveStepFormData();
    if (this.isStepValid()) {
      this.formNavigator.navigateToNextStep(this.stepRoute);
    }
  }

  protected getStepFormDataSnapshot() {
    return this.mainFormState.getStepFormDataSnapshot(this.stepId);
  }

  protected saveStepFormData() {
    return this.mainFormState.saveStepFormData(this.stepId, this.stepForm.value);
  }

  protected loadStepDataFromSnapshot() {
    const dataSnapshot = this.getStepFormDataSnapshot();
    Object.keys(this.stepForm.controls)
      .forEach(controlName => this.stepForm.get(controlName).setValue(dataSnapshot[controlName]));
  }

  protected abstract initStepFormControls();


}

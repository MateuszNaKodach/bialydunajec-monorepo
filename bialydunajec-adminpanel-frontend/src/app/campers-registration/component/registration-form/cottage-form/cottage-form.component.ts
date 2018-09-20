import {Component, OnInit} from '@angular/core';
import {RegistrationFormStepAbstractComponent} from '../registration-form-step.abstract-component';
import {CamperRegistrationFormStateService} from '../../../service/camper-registration-form-state.service';
import {CamperRegistrationFormNavigator} from '../../../service/camper-registration-form.navigator';
import {ActivatedRoute} from '@angular/router';
import {FormBuilder, Validators} from '@angular/forms';
import {StepId} from '../registration-form.config';

@Component({
  selector: 'bda-cottage-form',
  templateUrl: './cottage-form.component.html',
  styleUrls: ['./cottage-form.component.scss']
})
export class CottageFormComponent extends RegistrationFormStepAbstractComponent {

  constructor(
    mainFormState: CamperRegistrationFormStateService,
    formNavigator: CamperRegistrationFormNavigator,
    stepRoute: ActivatedRoute,
    private formBuilder: FormBuilder) {
    super(StepId.COTTAGE, mainFormState, formNavigator, stepRoute);
  }

  protected initStepFormControls() {
    this.stepForm = this.formBuilder.group({
        cottageId: [null, [Validators.required]]
      }
    );
  }

  get cottageId() {
    return this.stepForm.get('cottageId');
  }

}

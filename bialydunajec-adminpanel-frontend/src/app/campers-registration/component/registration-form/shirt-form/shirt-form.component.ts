import {Component} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {RegistrationFormStepAbstractComponent} from '../registration-form-step.abstract-component';
import {CamperRegistrationFormStateService} from '../../../service/camper-registration-form-state.service';
import {StepId} from '../registration-form.config';
import {Gender} from '../../../model/gender.model';
import {AngularFormHelper} from '../../../../shared/helper/angular-form.helper';
import {CamperRegistrationFormNavigator} from '../../../service/camper-registration-form.navigator';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'bda-shirt-form',
  templateUrl: './shirt-form.component.html',
  styleUrls: ['./shirt-form.component.scss']
})
export class ShirtFormComponent extends RegistrationFormStepAbstractComponent {

  constructor(
    mainFormState: CamperRegistrationFormStateService,
    formNavigator: CamperRegistrationFormNavigator,
    stepRoute: ActivatedRoute,
    private formBuilder: FormBuilder) {
    super(StepId.SHIRT, mainFormState, formNavigator, stepRoute);
  }

  protected initStepFormControls() {
    this.stepForm = this.formBuilder.group({
        color: [null, [Validators.required]],
        size: [null, Validators.required],
        clothType: [null, Validators.required]
      }
    );
  }


  get color() {
    return this.stepForm.get('color');
  }

  get size() {
    return this.stepForm.get('size');
  }

  get clothType() {
    return this.stepForm.get('clothType');
  }


  // TODO: On backend option names have to be unique!
  getShirtColorOptions() {
    return [
      'czarny',
      'czerwony',
      'zielony',
      'niebieski'
    ];
  }

  getShirtSizeOptions() {
    return [
      'S',
      'M',
      'L',
      'XL',
      'XXL'
    ];
  }

  getClothTypeOptions() {
    return [
      {id: Gender.FEMALE, name: 'Damska'},
      {id: Gender.MALE, name: 'Męska'}
    ];
  }

}

import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {RegistrationFormStepAbstractComponent} from '../registration-form-step.abstract-component';
import {CamperRegistrationFormStateService} from '../../../service/camper-registration-form-state.service';
import {CamperRegistrationFormNavigator} from '../../../service/camper-registration-form.navigator';
import {ActivatedRoute} from '@angular/router';
import {StepId} from '../registration-form.config';

// TODO: Read dynamic forms config: https://toddmotto.com/angular-dynamic-components-forms
@Component({
  selector: 'bda-transport-form',
  templateUrl: './transport-form.component.html',
  styleUrls: ['./transport-form.component.scss']
})
export class TransportFormComponent extends RegistrationFormStepAbstractComponent {
  formConfig = {
    title: 'Dojazd',
    description: 'Poinformuj nas jak dojedziesz do Białego Dunajca :) Więcej informacji na temat naszego autokaru znajdziesz tutaj.',
    controls: {
      meanOfTransport: {
        name: 'meanOfTransport',
        type: 'select',
        label: 'Dojazd na Obóz',
        placeholder: 'Wybierz formę dojazdu',
        validators: [Validators.required],
        options: [
          'Samochodem',
          'Pociągiem / autobusem',
          'Rowerem',
          'Na stopa',
          'Autokarem Białego Dunajca (+55 zł)'
        ]
      }
    }
  };


  constructor(
    mainFormState: CamperRegistrationFormStateService,
    formNavigator: CamperRegistrationFormNavigator,
    stepRoute: ActivatedRoute,
    private formBuilder: FormBuilder) {
    super(StepId.TRANSPORT, mainFormState, formNavigator, stepRoute);
  }

  protected initStepFormControls() {
    const controlsConfig = {};
    Object.values(this.formConfig.controls)
      .forEach(c => controlsConfig[c.name] = [null, c.validators]);
    this.stepForm = this.formBuilder.group(controlsConfig);
  }

  getFormControlByName(name: string) {
    return this.stepForm.get(name);
  }

}


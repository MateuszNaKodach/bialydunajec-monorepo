import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {RegistrationFormStepAbstractComponent} from '../registration-form-step.abstract-component';
import {CamperRegistrationFormStateService} from '../../../service/camper-registration-form-state.service';
import {CamperRegistrationFormNavigator} from '../../../service/camper-registration-form.navigator';
import {ActivatedRoute} from '@angular/router';
import {StepId} from '../registration-form.config';
import {TransportType} from '../../../model/transport-type.enum';
import {filter} from 'rxjs/operators';

// TODO: Read dynamic forms config: https://toddmotto.com/angular-dynamic-components-forms
@Component({
  selector: 'bda-transport-form',
  templateUrl: './transport-form.component.html',
  styleUrls: ['./transport-form.component.scss']
})
export class TransportFormComponent extends RegistrationFormStepAbstractComponent {
  TransportType = TransportType;

  // TODO: To remove formConfig - use standard approach. Registration form is to complex for create it from such object!
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
          {
            id: '1',
            name: 'Samochodem',
            transportType: TransportType.PRIVATE_CAR,
            additionalNotes: {
              title: 'Miejsce parkingowe',
              content: 'Jeśli chcesz mieć miejsce parkingowe pod swoją chatką, pamiętaj o kontakcie z szefem chatki zanim przyjedziesz!'
            }
          },
          {id: '2', name: 'Pociągiem / autobusem', transportType: TransportType.PUBLIC_TRANSPORT},
          {id: '3', name: 'Rowerem', transportType: TransportType.PRIVATE_TRANSPORT},
          {id: '4', name: 'Na stopa', transportType: TransportType.HITCH_HIKING},
          {id: '5', name: 'Autokarem Białego Dunajca (+55 zł)', transportType: TransportType.CAMP_TRANSPORT}
        ]
      }
    }
  };

  selectedTransportType: TransportType;
  showParkingSpaceInfo = false;

  additionalStepFormOptions = new Map<TransportType, FormGroup>([
    [TransportType.CAMP_TRANSPORT, this.formBuilder.group({
      destinationBusStop: [this.getStepFormDataSnapshot().campTransport ? this.getStepFormDataSnapshot().campTransport.destinationBusStop : null, [Validators.required]]
    })]
  ]);


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

    this.stepForm.get([this.formConfig.controls.meanOfTransport.name]).valueChanges
      .pipe(filter(value => value))
      .subscribe(value => {
          this.selectedTransportType = this.getMeanOfTransportOptionByName(value).transportType;
          this.showAdditionalContentForTransportType(this.selectedTransportType);
        }
      );
  }

  private showAdditionalContentForTransportType(transportType: TransportType) {
    this.stepForm.removeControl(TransportType.CAMP_TRANSPORT);
    this.showParkingSpaceInfo = false;
    if (transportType === TransportType.CAMP_TRANSPORT) {
      this.stepForm.addControl(TransportType.CAMP_TRANSPORT, this.additionalStepFormOptions.get(TransportType.CAMP_TRANSPORT));
    } else if (transportType === TransportType.PRIVATE_CAR) { // TODO: Change for additional message without new TransportType!
      this.showParkingSpaceInfo = true;
    }
    console.log(this.stepForm.value);
  }

  get campTransportDestinationBusStop() {
    return this.getFormControlByName([TransportType.CAMP_TRANSPORT, 'destinationBusStop']);
  }

  get meanOfTransport() {
    return this.getFormControlByName(this.formConfig.controls.meanOfTransport.name);
  }

  get meanOfTransportOptions() {
    return this.formConfig.controls.meanOfTransport.options
      .map(option => option.name);
  }

  private getMeanOfTransportOptionByName(name: string) {
    return this.formConfig.controls.meanOfTransport.options
      .find(o => o.name === name);
  }

  private getFormControlByName(name: string | string[]) {
    return this.stepForm.get(name);
  }

}


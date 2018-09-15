import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {RegistrationStepViewModel} from './registration-step.view-model';

@Component({
  selector: 'bda-registration-stepper',
  templateUrl: './registration-stepper.component.html',
  styleUrls: ['./registration-stepper.component.scss']
})
export class RegistrationStepperComponent implements OnInit {

  @Input() steps: RegistrationStepViewModel[];
  @Output() stepClicked = new EventEmitter<RegistrationStepViewModel>();

  constructor() {
  }

  ngOnInit() {
  }

  onStepClicked(step: RegistrationStepViewModel) {
    this.stepClicked.emit(step);
  }

}

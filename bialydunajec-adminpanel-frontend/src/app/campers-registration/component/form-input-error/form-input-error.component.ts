import {Component, Input, OnInit} from '@angular/core';
import {AbstractControl} from '@angular/forms';

@Component({
  selector: 'bda-form-input-error',
  templateUrl: './form-input-error.component.html',
  styleUrls: ['./form-input-error.component.scss']
})
export class FormInputErrorComponent implements OnInit {

  @Input() control: AbstractControl;
  @Input() messages: [{ error: string, message: string }];
  @Input() showOnlyFirst = false;

  constructor() {
  }

  ngOnInit() {
  }

  get errorMessages(): string[] {
    return this.messages
      .filter(m => (this.control.hasError(m.error)))
      .map(m => m.message);
  }

}

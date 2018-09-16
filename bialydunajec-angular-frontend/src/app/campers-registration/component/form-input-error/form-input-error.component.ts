import {Component, Input, OnInit} from '@angular/core';
import {AbstractControl} from '@angular/forms';

@Component({
  selector: 'bda-form-input-error',
  templateUrl: './form-input-error.component.html',
  styleUrls: ['./form-input-error.component.scss']
})
export class FormInputErrorComponent implements OnInit {

  @Input() control: AbstractControl;

  constructor() {
  }

  ngOnInit() {
  }

  get errorMessage(): string | null {
    const errors = this.control.errors;
    if (errors.invalidPesel) {
      return 'Niepoprawny numer PESEL';
    }
    return null;
  }

}

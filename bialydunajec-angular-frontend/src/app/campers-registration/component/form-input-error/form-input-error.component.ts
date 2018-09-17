import {Component, Input, OnChanges, OnInit} from '@angular/core';
import {AbstractControl} from '@angular/forms';
import {defaultErrorDefinitions} from './error-defs.default';

// https://almerosteyn.com/2016/03/angular2-form-validation-component
@Component({
  selector: 'bda-form-input-error',
  templateUrl: './form-input-error.component.html',
  styleUrls: ['./form-input-error.component.scss']
})
export class FormInputErrorComponent implements OnInit, OnChanges {

  @Input() label: string;
  @Input() control: AbstractControl;
  @Input() errorDefs: any;

  errorMessage = '';

  constructor() {
  }

  ngOnInit() {
  }

  ngOnChanges(changes: any): void {
    this.errorMessage = '';
    const errors = this.control.errors;
    if (errors) {
      Object.keys(this.errorDefs)
        .some(errorKey => {
          if (errors[errorKey]) {
            this.errorMessage = this.errorDefs[errorKey];
            return true;
          }
        });
    }
  }

  hasError() {
    return this.control.touched && this.control.invalid;
  }

}

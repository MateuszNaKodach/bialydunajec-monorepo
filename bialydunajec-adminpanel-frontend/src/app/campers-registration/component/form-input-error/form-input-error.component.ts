import {Component, Input, OnChanges, OnDestroy, OnInit} from '@angular/core';
import {AbstractControl} from '@angular/forms';
import {defaultErrorDefinitions} from './error-defs.default';
import {FormStatus} from '../../model/form-status.enum';
import {Subscription} from 'rxjs';

// https://almerosteyn.com/2016/03/angular2-form-validation-component
@Component({
  selector: 'bda-form-input-error',
  templateUrl: './form-input-error.component.html',
  styleUrls: ['./form-input-error.component.scss']
})
export class FormInputErrorComponent implements OnInit, OnDestroy {

  @Input() label: string;
  @Input() control: AbstractControl;
  @Input() errorDefs: any;

  errorMessage: string = null;

  private controlStatusSubscription: Subscription;

  constructor() {
  }

  ngOnInit() {
    this.errorDefs = {...this.errorDefs, ...defaultErrorDefinitions};

    this.updateControlErrors();
    this.controlStatusSubscription = this.control.statusChanges
      .subscribe(status => {
        if (status === FormStatus.INVALID) {
          this.updateControlErrors();
        } else {
          this.errorMessage = null;
        }
      });

  }

  private updateControlErrors() {
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

  ngOnDestroy() {
    this.controlStatusSubscription.unsubscribe();
  }

  isInvalid() {
    return this.control.touched && this.control.invalid;
  }

}

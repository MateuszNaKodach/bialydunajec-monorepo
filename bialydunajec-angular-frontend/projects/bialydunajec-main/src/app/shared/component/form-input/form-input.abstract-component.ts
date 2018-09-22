import {Input, OnDestroy, OnInit} from '@angular/core';
import {AbstractControl} from '@angular/forms';
import {Subscription} from 'rxjs';
import {defaultErrorDefinitions} from './error-defs.default';
import {FormStatus} from '../../model/form-status.enum';

export abstract class FormInputAbstractComponent implements OnInit, OnDestroy {

  private abstractControl: AbstractControl;
  private errorDefinitions: any;

  errorMessage: string = null;

  private controlStatusSubscription: Subscription;

  ngOnInit() {
    const formInputProperties = this.getFormInputProperties();
    this.abstractControl = formInputProperties.abstractControl;
    this.errorDefinitions = formInputProperties.errorDefinitions;

    this.errorDefinitions = {...this.errorDefinitions, ...defaultErrorDefinitions};

    this.updateControlErrors();
    this.controlStatusSubscription = this.abstractControl.statusChanges
      .subscribe(status => {
        if (status === FormStatus.INVALID) {
          this.updateControlErrors();
        } else {
          this.errorMessage = null;
        }
      });

  }

  protected abstract getFormInputProperties(): { abstractControl: AbstractControl, errorDefinitions: any };

  private updateControlErrors() {
    const errors = this.abstractControl.errors;
    if (errors) {
      Object.keys(this.errorDefinitions)
        .some(errorKey => {
          if (errors[errorKey]) {
            this.errorMessage = this.errorDefinitions[errorKey];
            return true;
          }
        });
    }
  }

  ngOnDestroy() {
    this.controlStatusSubscription.unsubscribe();
  }

  isInvalid() {
    return (this.abstractControl.touched || this.abstractControl.dirty) && this.abstractControl.invalid;
  }


}

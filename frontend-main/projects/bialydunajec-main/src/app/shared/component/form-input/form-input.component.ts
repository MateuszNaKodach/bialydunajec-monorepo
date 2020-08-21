import {Component, Input} from '@angular/core';
import {AbstractControl} from '@angular/forms';
import {FormInputAbstractComponent} from './form-input.abstract-component';

// https://almerosteyn.com/2016/03/angular2-form-validation-component
@Component({
  selector: 'bda-form-input',
  templateUrl: './form-input.component.html',
  styleUrls: ['./form-input.component.scss']
})
export class FormInputComponent extends FormInputAbstractComponent {

  @Input() label: string;
  @Input() control: AbstractControl;
  @Input() errorDefs: any;

  protected getFormInputProperties(): { abstractControl: AbstractControl; errorDefinitions: any } {
    return {abstractControl: this.control, errorDefinitions: this.errorDefs};
  }

}

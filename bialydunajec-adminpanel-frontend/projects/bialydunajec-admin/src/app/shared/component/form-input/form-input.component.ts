import {Component, Input} from '@angular/core';
import {FormInputAbstractComponent} from 'bialydunajec-commons';
import {AbstractControl} from '@angular/forms';

@Component({
  selector: 'bda-admin-form-input',
  templateUrl: './form-input.component.html',
  styleUrls: ['./form-input.component.less']
})
export class FormInputComponent extends FormInputAbstractComponent {

  @Input() label: string;
  @Input() control: AbstractControl;
  @Input() errorDefs: any;

  protected getFormInputProperties(): { abstractControl: AbstractControl; errorDefinitions: any } {
    return {abstractControl: this.control, errorDefinitions: this.errorDefs};
  }

}

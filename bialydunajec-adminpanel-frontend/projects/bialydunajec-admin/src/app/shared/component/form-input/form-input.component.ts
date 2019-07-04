import {Component, Input} from '@angular/core';
import {AbstractControl} from '@angular/forms';
import {FormInputAbstractComponent} from './form-input.abstract-component';

@Component({
  selector: '[bda-admin-form-input]',
  templateUrl: './form-input.component.html',
  styleUrls: ['./form-input.component.less']
})
export class FormInputComponent extends FormInputAbstractComponent {

  @Input() isEditMode = true;
  @Input() label: string;
  @Input() control: AbstractControl;
  @Input() errorDefs: any;

  protected getFormInputProperties(): { abstractControl: AbstractControl; errorDefinitions: any } {
    return {abstractControl: this.control, errorDefinitions: this.errorDefs};
  }


}

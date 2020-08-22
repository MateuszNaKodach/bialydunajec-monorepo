import {AbstractControl, FormGroup} from '@angular/forms';

export class AngularFormHelper {
  static markFormGroupTouched(formGroup: FormGroup) {
    this.executeOnAllFormGroupControls(formGroup, control => {
      if (!control.touched) {
        control.markAsTouched();
        control.updateValueAndValidity();
      }
    });
  }

  static markFormGroupDirty(formGroup: FormGroup) {
    this.executeOnAllFormGroupControls(formGroup, control => {
      if (!control.dirty) {
        control.markAsDirty();
        control.updateValueAndValidity();
      }
    });
  }

  static executeOnAllFormGroupControls(formGroup: FormGroup, functionToExecute: (control: AbstractControl) => any) {
    (<any>Object).values(formGroup.controls).forEach(control => {
      functionToExecute(control);

      if (control.controls) {
        this.executeOnAllFormGroupControls(control, functionToExecute);
      }
    });
  }
}

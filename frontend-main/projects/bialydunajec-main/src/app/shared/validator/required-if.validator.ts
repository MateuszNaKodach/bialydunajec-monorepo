import {ValidatorFn, ValidationErrors, AbstractControl} from '@angular/forms';

export function requiredIf(requiredIf: { value: boolean }): ValidatorFn {

  return (control: AbstractControl): ValidationErrors | null => {

    const value = control.value;

    if ((value == null || value == undefined || value == '') && requiredIf.value) {
      return {
        requiredIf: {condition: requiredIf.value}
      };
    }
    return null;
  };
}

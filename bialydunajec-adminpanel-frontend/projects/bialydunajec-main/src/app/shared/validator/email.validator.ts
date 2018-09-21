import {FormControl} from '@angular/forms';

export function emailValidator(control: FormControl) {
  if (!control.value.match(/^[a-z0-9!#$%&'*+\/=?^_`{|}~.-]+@[a-z0-9]([a-z0-9-]*[a-z0-9])?(\.[a-z0-9]([a-z0-9-]*[a-z0-9])?)+$/i)) {
    return {'email': control.value};
  } else {
    return null;
  }
}

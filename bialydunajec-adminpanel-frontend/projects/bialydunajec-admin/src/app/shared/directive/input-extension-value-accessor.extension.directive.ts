import {Directive, forwardRef, Renderer2, ElementRef, Input} from '@angular/core';
import {DefaultValueAccessor, NG_VALUE_ACCESSOR} from '@angular/forms';

// Source: https://stackoverflow.com/questions/38527535/angular-2-set-null-as-empty-string-value-for-input-field

export const VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => InputExtensionValueAccessor),
  multi: true
};

@Directive({
  selector: 'input:not([type]),input[type=text],input[type=password],input[type=email],input[type=tel],textarea',
  host: {
    '(input)': '_handleInput($event.target.value)',
    '(blur)': 'onTouched()',
    '(compositionstart)': '_compositionStart()',
    '(compositionend)': '_compositionEnd($event.target.value)'
  },
  providers: [VALUE_ACCESSOR]
})
export class InputExtensionValueAccessor extends DefaultValueAccessor {
  // HACK: Allows use of DefaultValueAccessor as base
  // (https://github.com/angular/angular/issues/9146)
  static decorators = null;

  constructor(renderer: Renderer2, elementRef: ElementRef) {
    super(renderer, elementRef, (null as any));
  }

  registerOnChange(fn: (_: any) => void): void {
    super.registerOnChange(value => {
      // Convert empty string from the view to null for the model
      fn(value === '' ? null : value);
    });
  }
}

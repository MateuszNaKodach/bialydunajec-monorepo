import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {OptionSelected} from './event/option-selected.event';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from '@angular/forms';

@Component({
  selector: 'bda-dual-toggle-button',
  templateUrl: './dual-toggle-button.component.html',
  styleUrls: ['./dual-toggle-button.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: DualToggleButtonComponent,
      multi: true
    }
  ]
})
export class DualToggleButtonComponent implements OnInit, ControlValueAccessor {

  @Input() selectionColor = 'orange';
  @Input() selected;
  @Input() options: { left: { name: string, icon: string, value: any }, right: { name: string, icon: string, value: any } };
  @Output() optionSelected = new EventEmitter<OptionSelected>();
  onChange;

  constructor() {
  }

  ngOnInit() {
  }

  onOptionSelected(option: string) {
    this.selected = option;
    this.optionSelected.emit(new OptionSelected(this.options[option].name));

    const selectedValue = this.options[option].value;
    this.onChange(selectedValue === null ? this.options[option].name : selectedValue);
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
  }

  setDisabledState(isDisabled: boolean): void {
    // TODO: Implement disabled!
  }

  writeValue(obj: any): void {
    this.selected = Object.keys(this.options)
      .find(o => o['name'] === obj);
  }


}

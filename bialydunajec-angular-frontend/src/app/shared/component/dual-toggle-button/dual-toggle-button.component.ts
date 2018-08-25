import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {OptionSelected} from './event/option-selected.event';

@Component({
  selector: 'bda-dual-toggle-button',
  templateUrl: './dual-toggle-button.component.html',
  styleUrls: ['./dual-toggle-button.component.scss']
})
export class DualToggleButtonComponent implements OnInit {

  @Input() selectionColor = 'orange';
  @Input() selected;
  @Input() options: { left: { name: string, icon: string }, right: { name: string, icon: string } };
  @Output() optionSelected = new EventEmitter<OptionSelected>();

  constructor() {
  }

  ngOnInit() {
  }

  onOptionSelected(option: string) {
    this.selected = option;
    this.optionSelected.emit(new OptionSelected(this.options[option].name));
  }
}

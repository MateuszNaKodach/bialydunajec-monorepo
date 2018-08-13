import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'bda-dual-toggle-button',
  templateUrl: './dual-toggle-button.component.html',
  styleUrls: ['./dual-toggle-button.component.scss']
})
export class DualToggleButtonComponent implements OnInit {

  @Input() selectionColor = 'orange';
  @Input() selected;
  @Input() options: { left: string, right: string };
  @Output() optionSelected = new EventEmitter<string>();

  constructor() {
  }

  ngOnInit() {

  }

  onOptionSelected(option: string) {
    this.selected = option;
    this.optionSelected.emit(this.options[option]);
  }

}

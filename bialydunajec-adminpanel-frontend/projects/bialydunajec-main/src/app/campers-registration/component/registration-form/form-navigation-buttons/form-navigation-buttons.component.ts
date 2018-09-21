import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'bda-form-navigation-buttons',
  templateUrl: './form-navigation-buttons.component.html',
  styleUrls: ['./form-navigation-buttons.component.scss']
})
export class FormNavigationButtonsComponent implements OnInit {

  @Input() config: { left: { text: string, disabled: boolean }, right: { text: string, disabled: boolean } };
  @Output() clickLeft = new EventEmitter<void>();
  @Output() clickRight = new EventEmitter<void>();

  constructor() {
  }

  ngOnInit() {
  }

}

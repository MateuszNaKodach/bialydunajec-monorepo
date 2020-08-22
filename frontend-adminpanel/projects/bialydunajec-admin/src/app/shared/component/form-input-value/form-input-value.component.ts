import {Component, Input, OnInit} from '@angular/core';
import {AbstractControl} from '@angular/forms';

@Component({
  selector: 'bda-admin-form-input-value',
  templateUrl: './form-input-value.component.html',
  styleUrls: ['./form-input-value.component.less']
})
export class FormInputValueComponent implements OnInit {

  @Input() control: AbstractControl;

  constructor() {
  }

  ngOnInit() {
  }

}

import { Component, OnInit } from '@angular/core';
import {RomanNumerals} from '../../../shared/helper/RomanNumerals';

@Component({
  selector: 'bda-admin-camp-edition-edit',
  templateUrl: './camp-edition-edit.component.html',
  styleUrls: ['./camp-edition-edit.component.less']
})
export class CampEditionEditComponent implements OnInit {

  RomanNumerals = RomanNumerals;

  constructor() { }

  ngOnInit() {
  }

}

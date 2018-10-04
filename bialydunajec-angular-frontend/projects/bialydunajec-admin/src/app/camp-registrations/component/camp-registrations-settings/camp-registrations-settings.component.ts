import {Component, OnInit} from '@angular/core';
import {RomanNumerals} from '../../../shared/helper/RomanNumerals';

@Component({
  selector: 'bda-admin-camp-registrations-settings',
  templateUrl: './camp-registrations-settings.component.html',
  styleUrls: ['./camp-registrations-settings.component.less']
})
export class CampRegistrationsSettingsComponent implements OnInit {

  RomanNumerals = RomanNumerals;

  constructor() {
  }

  ngOnInit() {
  }

}

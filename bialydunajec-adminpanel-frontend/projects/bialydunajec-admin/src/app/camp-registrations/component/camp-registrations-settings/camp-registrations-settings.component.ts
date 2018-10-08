import {Component, OnInit} from '@angular/core';
import {RomanNumerals} from '../../../shared/helper/RomanNumerals';
import {CampRegistrationsEndpoint} from '../../service/rest/camp-registrations.endpoint';
import {Observable} from 'rxjs';
import {CampEditionResponse} from '../../../camp-edition/service/rest/response/camp-edition.response';

@Component({
  selector: 'bda-admin-camp-registrations-settings',
  templateUrl: './camp-registrations-settings.component.html',
  styleUrls: ['./camp-registrations-settings.component.less']
})
export class CampRegistrationsSettingsComponent implements OnInit {

  RomanNumerals = RomanNumerals;
  availableCampEditions: Observable<CampEditionResponse[]>;

  constructor(private campRegistrationsEndpoint: CampRegistrationsEndpoint) {
  }

  ngOnInit() {
    this.availableCampEditions = this.campRegistrationsEndpoint.getAllCampEditions();
  }

  onCampEditionIdSelected($selectedCampEditionId: number) {
    console.log('Selected Camp Edition Id:', $selectedCampEditionId);
  }
}

import {Component, OnInit} from '@angular/core';
import {InProgressCampRegistrationsEndpoint} from '../../service/rest/in-progress-camp-registrations.endpoint';
import {Observable} from 'rxjs';
import {RomanNumerals} from '../../../../../../bialydunajec-admin/src/app/shared/helper/RomanNumerals';
import {CampRegistrationsEditionResponse} from '../../../../../../bialydunajec-admin/src/app/camp-registrations/service/rest/response/camp-registrations-edition.response';
import {campersRegistrationRoutingPaths} from '../../campers-registration-routing.paths';

@Component({
  selector: 'bda-registration-start',
  templateUrl: './registration-start.component.html',
  styleUrls: ['./registration-start.component.scss']
})
export class RegistrationStartComponent implements OnInit {

  campersRegistrationRoutingPaths = campersRegistrationRoutingPaths;
  RomanNumerals = RomanNumerals;
  dateFormat = 'DD.MM.YYYY';

  campRegistrations: Observable<CampRegistrationsEditionResponse>;

  constructor(private inProgressCampRegistrationsEndpoint: InProgressCampRegistrationsEndpoint) {
  }

  ngOnInit() {
    this.campRegistrations = this.inProgressCampRegistrationsEndpoint.getInProgressCampRegistrationsEdition();
  }

}

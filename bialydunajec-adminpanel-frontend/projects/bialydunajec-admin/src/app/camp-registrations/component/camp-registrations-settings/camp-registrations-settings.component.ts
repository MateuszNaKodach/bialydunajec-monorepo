import {Component, OnInit} from '@angular/core';
import {RomanNumerals} from '../../../shared/helper/RomanNumerals';
import {CampRegistrationsEndpoint} from '../../service/rest/camp-registrations.endpoint';
import {Observable} from 'rxjs';
import {CampEditionResponse} from '../../../camp-edition/service/rest/response/camp-edition.response';
import {CampRegistrationsEditionResponse} from '../../service/rest/response/camp-registrations-edition.response';
import {RegistrationsStatus} from '../../service/rest/response/camp-registrations.response';
import {finalize, tap} from 'rxjs/operators';

@Component({
  selector: 'bda-admin-camp-registrations-settings',
  templateUrl: './camp-registrations-settings.component.html',
  styleUrls: ['./camp-registrations-settings.component.less']
})
export class CampRegistrationsSettingsComponent implements OnInit {

  RomanNumerals = RomanNumerals;
  availableCampEditions: Observable<CampEditionResponse[]>;
  selectedCampRegistrations: Observable<CampRegistrationsEditionResponse>;
  changingStatusInProgress = false;
  private currentCampRegistrationsEdition: CampRegistrationsEditionResponse;

  constructor(private campRegistrationsEndpoint: CampRegistrationsEndpoint) {
  }

  ngOnInit() {
    this.availableCampEditions = this.campRegistrationsEndpoint.getAllCampEditions();
  }

  onCampEditionIdSelected(selectedCampEditionId: number) {
    this.updateCampRegistrationsEdition(selectedCampEditionId);
  }

  updateCampRegistrationsEdition(selectedCampEditionId: number) {
    this.selectedCampRegistrations = this.campRegistrationsEndpoint.getCampRegistrationsEditionById(selectedCampEditionId)
      .pipe(tap(response => this.currentCampRegistrationsEdition = response));
  }

  startCampRegistrations() {
    this.changingStatusInProgress = true;
    this.campRegistrationsEndpoint.startCampRegistrationsEditionById(this.currentCampRegistrationsEdition.campRegistrationsEditionId)
      .pipe(
        finalize(() => this.changingStatusInProgress = false)
      )
      .subscribe(response => {
          console.log(response);
          this.updateCampRegistrationsEdition(this.currentCampRegistrationsEdition.campRegistrationsEditionId);
        },
        error => console.log(error)
      );
  }

  suspendCampRegistrations() {
    this.changingStatusInProgress = true;
    this.campRegistrationsEndpoint.suspendCampRegistrationsEditionById(this.currentCampRegistrationsEdition.campRegistrationsEditionId)
      .pipe(
        finalize(() => this.changingStatusInProgress = false)
      )
      .subscribe(response => {
          console.log(response);
          this.updateCampRegistrationsEdition(this.currentCampRegistrationsEdition.campRegistrationsEditionId);
        },
        error => console.log(error)
      );
  }

  unsuspendCampRegistrations() {
    this.changingStatusInProgress = true;
    this.campRegistrationsEndpoint.unsuspendCampRegistrationsEditionById(this.currentCampRegistrationsEdition.campRegistrationsEditionId)
      .pipe(
        finalize(() => this.changingStatusInProgress = false)
      )
      .subscribe(response => {
          console.log(response);
          this.updateCampRegistrationsEdition(this.currentCampRegistrationsEdition.campRegistrationsEditionId);
        },
        error => console.log(error)
      );
  }

  finishCampRegistrations() {
    console.log('finish');
  }
}

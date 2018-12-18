import {Component, OnInit} from '@angular/core';
import {RomanNumerals} from '../../../shared/helper/RomanNumerals';
import {CampRegistrationsEndpoint} from '../../service/rest/camp-registrations.endpoint';
import {Observable, Observer} from 'rxjs';
import {CampEditionResponse} from '../../../camp-edition/service/rest/response/camp-edition.response';
import {CampRegistrationsEditionResponse} from '../../service/rest/response/camp-registrations-edition.response';
import {RegistrationsStatus} from '../../service/rest/response/camp-registrations.response';
import {finalize, tap} from 'rxjs/operators';
import {AlertViewModel} from '../../../shared/view-model/ng-zorro/alert.view-model';
import {HttpResponseHelper} from '../../../shared/helper/HttpResponseHelper';

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
  lastAlert: AlertViewModel;
  private registrationsStatusUpdateObserver: Observer<any> = {
    next: response => {
      this.lastAlert = {
        type: 'success',
        message: `Zapisy na ${RomanNumerals.numberToRoman(this.currentCampRegistrationsEdition.campRegistrationsEditionId)} Edycję Obozu`,
        description: 'Status zapisów został zmieniony.'
      };
    },
    error: response => {
      console.log(response);
      const error = response.error;
      const restErrors = response.error.restErrors;
      if (HttpResponseHelper.isStatus4xx(response) && restErrors) {
        this.lastAlert = {
          type: 'error',
          message: `Zapisy na ${RomanNumerals.numberToRoman(this.currentCampRegistrationsEdition.campRegistrationsEditionId)} Edycję Obozu`,
          description: 'Status zapisów nie został zmieniony, z powodu złamania reguł:' +
            restErrors.map((e: string) => ` ${e}`)
        };
      } else if (response.status === 0) {
        this.lastAlert = {
          type: 'error',
          message: `Zapisy na ${RomanNumerals.numberToRoman(this.currentCampRegistrationsEdition.campRegistrationsEditionId)} Edycję Obozu`,
          description: 'Status zapisów nie został zmieniony, z powodu braku odpowiedzi serwera.'
        };
      } else {
        this.lastAlert = {
          type: 'error',
          message: 'Nowa Chatka Obozowa',
          description: `Status zapisów nie został zmieniony, z powodu błędu 
                  (jeśli nie wiesz co zrobić, to skontaktuj się z administratorem): \n ${error.message}`
        };
      }
    },
    complete: () => {
    }
  };

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
        tap(_ => this.updateCampRegistrationsEdition(this.currentCampRegistrationsEdition.campRegistrationsEditionId)),
        finalize(() => this.changingStatusInProgress = false)
      )
      .subscribe(this.registrationsStatusUpdateObserver);
  }

  suspendCampRegistrations() {
    this.changingStatusInProgress = true;
    this.campRegistrationsEndpoint.suspendCampRegistrationsEditionById(this.currentCampRegistrationsEdition.campRegistrationsEditionId)
      .pipe(
        tap(_ => this.updateCampRegistrationsEdition(this.currentCampRegistrationsEdition.campRegistrationsEditionId)),
        finalize(() => this.changingStatusInProgress = false)
      )
      .subscribe(this.registrationsStatusUpdateObserver);
  }

  unsuspendCampRegistrations() {
    this.changingStatusInProgress = true;
    this.campRegistrationsEndpoint.unsuspendCampRegistrationsEditionById(this.currentCampRegistrationsEdition.campRegistrationsEditionId)
      .pipe(
        tap(_ => this.updateCampRegistrationsEdition(this.currentCampRegistrationsEdition.campRegistrationsEditionId)),
        finalize(() => this.changingStatusInProgress = false)
      )
      .subscribe(this.registrationsStatusUpdateObserver);
  }

  finishCampRegistrations() {
    console.log('finish');
  }
}

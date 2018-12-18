import {Component, OnDestroy, OnInit} from '@angular/core';
import {CampRegistrationsEndpoint} from '../../service/rest/camp-registrations.endpoint';
import {CampParticipantResponse, ParticipationStatusDto} from '../../service/rest/response/camp-participant.response';
import {Observable} from 'rxjs';
import {CampEditionResponse} from '../../../camp-edition/service/rest/response/camp-edition.response';
import {finalize} from 'rxjs/operators';
import {EventSourcePolyfill} from 'ng-event-source';
import {environment} from '../../../../environments/environment';
import {EventType} from '../../../email-message/service/rest/event/event-type';
import {PaymentCommitmentType} from '../../../payments/service/rest/read-model/payment-commitment.read-model';
import {FormGroup} from '@angular/forms';
import {campRegistrationsRoutingPaths} from '../../camp-registrations-routing.paths';
import {AuthService} from '../../../auth/service/auth.service';

@Component({
  selector: 'bda-admin-camp-participant-list',
  templateUrl: './camp-participant-list.component.html',
  styleUrls: ['./camp-participant-list.component.less']
})
export class CampParticipantListComponent implements OnInit, OnDestroy {

  campRegistrationsRoutingPaths = campRegistrationsRoutingPaths;

  availableCampEditions: Observable<CampEditionResponse[]>;
  private campParticipants: CampParticipantResponse[] = [];
  campParticipantsSearchResult: CampParticipantResponse[] = [];


  //Chart UI
  campParticipantsByCottageStats: any[] = [];
  campCapacity = 0;
  registeredCampParticipants = 0;

  //Table UI
  currentCampEdition: number;
  tableIsLoading = false;
  newCampParticipantRegistered = false;
  searchingActive = false;


  private eventSource: EventSourcePolyfill;

  constructor(private campRegistrationsEndpoint: CampRegistrationsEndpoint, private authService: AuthService) {
  }

  ngOnInit() {
    this.availableCampEditions = this.campRegistrationsEndpoint.getAllCampEditions();
    this.observeCampParticipantProjectedEvents();
  }

  onCampEditionIdSelected(selectedCampEditionId: number) {
    this.currentCampEdition = selectedCampEditionId;
    this.updateCampRegistrationsTable();
    this.loadCampRegistrationsEditionStats();
  }

  private updateCampRegistrationsTable() {
    this.updateCampRegistrations(this.currentCampEdition);
  }

  private onCampRegistrationsReload() {
    this.updateCampRegistrationsTable();
    this.loadCampRegistrationsEditionStats();
  }

  private updateCampRegistrations(selectedCampEditionId: number) {
    this.newCampParticipantRegistered = false;
    this.tableIsLoading = true;
    this.campRegistrationsEndpoint.getCampParticipantsByCampRegistrationsEditionId(selectedCampEditionId)
      .pipe(
        finalize(() => {
          this.tableIsLoading = false;
        }),
      )
      .subscribe(
        (response: CampParticipantResponse[]) => {
          this.campParticipants = response;
          if (!this.searchingActive) {
            this.campParticipantsSearchResult = this.campParticipants;
          }
        }
      );
  }

  loadCampRegistrationsEditionStats() {
    this.campRegistrationsEndpoint.getCampRegistrationsStatisticsByCampRegistrationsEditionId(this.currentCampEdition)
      .subscribe(response => {
        const stats = response.cottagesStats;
        this.campCapacity = response.campCapacity;
        this.registeredCampParticipants = response.registeredCampParticipants;
        this.campParticipantsByCottageStats = stats
          .map(it => {
            return {name: it.cottageName, value: it.maleCampParticipantsAmount + it.femaleCampParticipantsAmount};
          });
      });
  }

  verifyCampParticipantRegistrationByAuthorized(campParticipant: CampParticipantResponse) {
    //TODO: Error handling!
    this.campRegistrationsEndpoint.verifyCampParticipantRegistrationByAuthorized(campParticipant.campParticipantRegistrationId)
      .subscribe();
  }

  private observeCampParticipantProjectedEvents() {
    this.eventSource = new EventSourcePolyfill(
      `${environment.restApi.baseUrl}/rest-api/v1/admin/camp-participant/projected-events-stream`, {
        headers: {
          'Authorization': `bearer ${this.authService.userOAuthToken.access_token}`
        }
      }
    );

    this.eventSource.onmessage = (event => {
      const data: any = JSON.parse(event.data);
      switch (data.eventType) {
        case EventType.CAMP_PARTICIPANT_CONFIRMED: {
          let campParticipant = this.campParticipants.find(it => it.campParticipantId === data.payload.campParticipantId);
          campParticipant.participationStatus = data.payload.snapshot.participationStatus;

          campParticipant = this.campParticipantsSearchResult.find(it => it.campParticipantId === data.payload.campParticipantId);
          campParticipant.participationStatus = data.payload.snapshot.participationStatus;
          break;
        }
        case EventType.COMMITMENT_PAID: {
          let campParticipant = this.campParticipants.find(it => it.campParticipantId === data.payload.campParticipantId);
          this.updateCampParticipantFromEventData(data, campParticipant);
          campParticipant = this.campParticipantsSearchResult.find(it => it.campParticipantId === data.payload.campParticipantId);
          this.updateCampParticipantFromEventData(data, campParticipant);

          break;
        }
        case EventType.CAMP_PARTICIPANT_REGISTERED: {
          this.newCampParticipantRegistered = true;
          break;
        }
      }
    });
  }

  private updateCampParticipantFromEventData(data: any, campParticipant) {
    switch (data.payload.commitmentType) {
      case PaymentCommitmentType.CAMP_BUS_SEAT: {
        campParticipant.campBusSeatPaidDate = data.payload.paidDate;
        break;
      }
      case PaymentCommitmentType.CAMP_DOWN_PAYMENT: {
        campParticipant.downPaymentPaidDate = data.payload.paidDate;
        break;
      }
      case PaymentCommitmentType.CAMP_PARTICIPATION: {
        campParticipant.campParticipationPaidDate = data.payload.paidDate;
        break;
      }
    }
  }

  onSubmitSearch(form: FormGroup) {
    const formValue = form.value;
    const firstName = formValue['firstName'];
    const lastName = formValue['lastName'];
    const pesel = formValue['pesel'];
    const phoneNumber = formValue['phoneNumber'];
    const emailAddress = formValue['emailAddress'];
    const cottage = formValue['cottage'];


    if (!firstName && !lastName && !pesel && !phoneNumber && !emailAddress && !cottage) {
      this.resetSearchResult(form);
    } else {
      this.campParticipantsSearchResult =
        this.campParticipants.filter(c => {
          const personalData = c.currentCamperData.personalData;
          return !((firstName && !personalData.firstName.toLowerCase().includes(firstName.toLowerCase()))
            || (lastName && !personalData.lastName.toLowerCase().includes(lastName.toLowerCase()))
            || (pesel && personalData.pesel && !personalData.pesel.toLowerCase().includes(pesel.toLowerCase()))
            || (phoneNumber && !c.currentCamperData.phoneNumber.toLowerCase().includes(phoneNumber.toLowerCase()))
            || (emailAddress && !c.currentCamperData.emailAddress.toLowerCase().includes(emailAddress.toLowerCase()))
            || (cottage && !c.currentCamperData.cottage.cottageName.toLowerCase().includes(cottage.toLowerCase())));
        });
      this.searchingActive = true;
    }
  }


  resetSearchResult(form: FormGroup) {
    form.reset();
    this.campParticipantsSearchResult = this.campParticipants;
    this.searchingActive = false;
  }

  ngOnDestroy(): void {
    this.eventSource.close();
  }


}

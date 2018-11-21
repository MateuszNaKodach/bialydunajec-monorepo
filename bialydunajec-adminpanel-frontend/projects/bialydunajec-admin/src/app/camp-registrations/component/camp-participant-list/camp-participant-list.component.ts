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

@Component({
  selector: 'bda-admin-camp-participant-list',
  templateUrl: './camp-participant-list.component.html',
  styleUrls: ['./camp-participant-list.component.less']
})
export class CampParticipantListComponent implements OnInit, OnDestroy {

  availableCampEditions: Observable<CampEditionResponse[]>;
  campParticipants: CampParticipantResponse[] = [];

  currentCampEdition: number;
  tableIsLoading = false;
  newCampParticipantRegistered = false;

  private eventSource: EventSourcePolyfill;

  constructor(private campRegistrationsEndpoint: CampRegistrationsEndpoint) {
  }

  ngOnInit() {
    this.availableCampEditions = this.campRegistrationsEndpoint.getAllCampEditions();
    this.observeCampParticipantProjectedEvents();
  }

  onCampEditionIdSelected(selectedCampEditionId: number) {
    this.currentCampEdition = selectedCampEditionId;
    this.updateCampRegistrationsTable(selectedCampEditionId);
  }

  private updateCampRegistrationsTable(selectedCampEditionId: number) {
    this.updateCampRegistrations(selectedCampEditionId);
  }

  private updateCampRegistrations(selectedCampEditionId: number) {
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
        }
      );
  }

  private observeCampParticipantProjectedEvents() {
    this.eventSource = new EventSourcePolyfill(
      `${environment.restApi.baseUrl}/rest-api/v1/admin/camp-participant/projected-events-stream`, {}
    );

    this.eventSource.onmessage = (event => {
      const data: any = JSON.parse(event.data);
      switch (data.eventType) {
        case EventType.CAMP_PARTICIPANT_CONFIRMED: {
          const campParticipant = this.campParticipants.find(it => it.campParticipantId === data.payload.campParticipantId);
          campParticipant.participationStatus = data.payload.participationStatus;
          break;
        }
        case EventType.COMMITMENT_PAID: {
          const campParticipant = this.campParticipants.find(it => it.campParticipantId === data.payload.campParticipantId);

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
          break;
        }
        case EventType.CAMP_PARTICIPANT_REGISTERED: {
          this.newCampParticipantRegistered = true;
          break;
        }
      }
    });
  }

  ngOnDestroy(): void {
    this.eventSource.close();
  }


}

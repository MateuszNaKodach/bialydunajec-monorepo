import {Component, OnDestroy, OnInit} from '@angular/core';
import {PaymentCommitmentEndpoint} from '../../service/rest/payment-commitment.endpoint';
import {PaymentCommitmentReadModel, PaymentCommitmentType} from '../../service/rest/read-model/payment-commitment.read-model';
import {Observable} from 'rxjs';
import {CampEditionResponse} from '../../../camp-edition/service/rest/response/camp-edition.response';
import {CampRegistrationsEndpoint} from '../../../camp-registrations/service/rest/camp-registrations.endpoint';
import {NzModalService} from 'ng-zorro-antd';
import {PayCommitmentRequest} from '../../service/rest/request/pay-commitment.request';
import {AlertViewModel} from '../../../shared/view-model/ng-zorro/alert.view-model';
import {tap} from 'rxjs/operators';
import {RequestErrorObserverBuilder} from '../../../../../../bialydunajec-main/src/app/campers-registration/component/registration-form/registration-summary/registration-summary.component';
import {EventSourcePolyfill} from 'ng-event-source';
import {environment} from '../../../../environments/environment';
import {EventType} from '../../../email-message/service/rest/event/event-type';

@Component({
  selector: 'bda-admin-payments-list',
  templateUrl: './payments-list.component.html',
  styleUrls: ['./payments-list.component.less']
})
export class PaymentsListComponent implements OnInit, OnDestroy {

  PaymentCommitmentType = PaymentCommitmentType;
  availableCampEditions: Observable<CampEditionResponse[]>;
  currentCampEdition: number;
  selectedPaymentCommitmentType = PaymentCommitmentType.CAMP_DOWN_PAYMENT;
  lastAlert: AlertViewModel;

  private paymentCommitments: PaymentCommitmentReadModel[];
  filteredPaymentCommitments: PaymentCommitmentReadModel[];

  private eventSource: EventSourcePolyfill;

  constructor(
    private paymentCommitmentEndpoint: PaymentCommitmentEndpoint,
    private campRegistrationsEndpoint: CampRegistrationsEndpoint,
    private modalService: NzModalService
  ) {
  }

  ngOnInit() {
    this.availableCampEditions = this.campRegistrationsEndpoint.getAllCampEditions();
    this.observePaymentCommitmentsProjectedEvents();
  }

  onCampEditionIdSelected(selectedCampEditionId: number) {
    this.currentCampEdition = selectedCampEditionId;
    this.reloadPaymentCommitments();
  }

  reloadPaymentCommitments() {
    this.paymentCommitmentEndpoint.getAllPaymentCommitmentByCampRegistrationsEditionId(this.currentCampEdition)
      .subscribe(response => {
        this.paymentCommitments = response;
        this.filteredPaymentCommitments = this.paymentCommitments.filter(it => it.type === this.selectedPaymentCommitmentType);
      });
  }

  showPayForCommitmentConfirmation(data: PaymentCommitmentReadModel) {
    this.modalService.confirm({
      nzTitle: '<i>Czy na pewno chcesz zaksięgować tą wpłatę?</i>',
      nzContent: '<b>Po zaksięgowaniu wpłaty uczestnik Obozu zostanie poinformowany o tym wiadomością e-mail.</b>',
      nzOnOk: () => {
        const requestBody = new PayCommitmentRequest(data.campParticipantCottageAccountId, false);
        let request: Observable<Object> = null;
        switch (data.type) {
          case PaymentCommitmentType.CAMP_BUS_SEAT: {
            request = this.paymentCommitmentEndpoint.payForCampBus(requestBody);
            break;
          }
          case PaymentCommitmentType.CAMP_DOWN_PAYMENT: {
            request = this.paymentCommitmentEndpoint.payForDownPayment(requestBody);
            break;
          }
          case PaymentCommitmentType.CAMP_PARTICIPATION: {
            request = this.paymentCommitmentEndpoint.payForCampParticipation(requestBody);
            break;
          }
        }

        request
          .pipe(
            tap(this.lastAlert = null)
          )
          .subscribe(_ => {
              this.lastAlert = {
                type: 'success',
                message: `${data.campParticipant.firstName} ${data.campParticipant.lastName} - Opłata`,
                description: 'Opłata została poprawnie zaksięgowana!'
              };
            },
            error => {
              RequestErrorObserverBuilder
                .anyError(_ => {
                  this.lastAlert = {
                    type: 'error',
                    message: `${data.campParticipant.firstName} ${data.campParticipant.lastName} - Opłata`,
                    description: 'Zaksięgowanie opłaty nie powiodło się.'
                  };
                })
                .getRequestErrorObserver();
            });
      }
    });
  }

  showCancelCommitmentPaymentConfirmation(data: PaymentCommitmentReadModel) {
    this.modalService.confirm({
      nzTitle: '<i>Czy na pewno chcesz oznaczyć zobowiązanie jako nieopłacone?</i>',
      nzContent: '<b>Po anulowaniu wpłaty uczestnik Obozu zostanie poinformowany o tym wiadomością e-mail.</b>',
      nzOnOk: () => {

      }
    });
  }


  onSelectedPaymentCommitmentTypeChange($event) {
    this.reloadPaymentCommitments();
    this.filteredPaymentCommitments = this.paymentCommitments.filter(it => it.type === this.selectedPaymentCommitmentType);
  }

  observePaymentCommitmentsProjectedEvents() {
    this.eventSource = new EventSourcePolyfill(
      `${environment.restApi.baseUrl}/rest-api/v1/admin/payment-commitment/projected-events-stream`, {}
    );
    console.log(this.eventSource);
    this.eventSource.onmessage = (event => {
      const data: any = JSON.parse(event.data);
      switch (data.eventType) {
        case EventType.COMMITMENT_PAID: {
          const payment = this.filteredPaymentCommitments.find(it => it.paymentCommitmentId === data.payload.paymentCommitmentId);
          payment.paidDate = data.createdDate;
          payment.paid = true;
          console.log('PAID', payment);
          break;
        }
      }
    });
  }

  ngOnDestroy(): void {
  }

}

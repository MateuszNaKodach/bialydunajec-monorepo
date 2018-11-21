import {Component, OnInit} from '@angular/core';
import {PaymentCommitmentEndpoint} from '../../service/rest/payment-commitment.endpoint';
import {PaymentCommitmentReadModel, PaymentCommitmentType} from '../../service/rest/read-model/payment-commitment.read-model';
import {Observable} from 'rxjs';
import {CampEditionResponse} from '../../../camp-edition/service/rest/response/camp-edition.response';
import {CampRegistrationsEndpoint} from '../../../camp-registrations/service/rest/camp-registrations.endpoint';
import {NzModalService} from 'ng-zorro-antd';
import {PayCommitmentRequest} from '../../service/rest/request/pay-commitment.request';

@Component({
  selector: 'bda-admin-payments-list',
  templateUrl: './payments-list.component.html',
  styleUrls: ['./payments-list.component.less']
})
export class PaymentsListComponent implements OnInit {

  availableCampEditions: Observable<CampEditionResponse[]>;
  currentCampEdition: number;

  paymentCommitments$: Observable<PaymentCommitmentReadModel[]>;

  constructor(
    private paymentCommitmentEndpoint: PaymentCommitmentEndpoint,
    private campRegistrationsEndpoint: CampRegistrationsEndpoint,
    private modalService: NzModalService
  ) {
  }

  ngOnInit() {
    this.availableCampEditions = this.campRegistrationsEndpoint.getAllCampEditions();
  }

  onCampEditionIdSelected(selectedCampEditionId: number) {
    this.currentCampEdition = selectedCampEditionId;
    this.reloadPaymentCommitments();
  }

  reloadPaymentCommitments() {
    this.paymentCommitments$ = this.paymentCommitmentEndpoint.getAllPaymentCommitmentByCampRegistrationsEditionId(this.currentCampEdition);
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
          .subscribe(_ => this.reloadPaymentCommitments());
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


}

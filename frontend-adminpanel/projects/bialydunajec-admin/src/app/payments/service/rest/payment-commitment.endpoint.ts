import {Injectable} from '@angular/core';
import {AbstractEndpoint} from '../../../shared/service/rest/abstract.endpoint';
import {HttpClient} from '@angular/common/http';
import {AuthService} from '../../../auth/service/auth.service';
import {PaymentCommitmentReadModel} from './read-model/payment-commitment.read-model';
import {PayCommitmentRequest} from './request/pay-commitment.request';

@Injectable({
  providedIn: 'root'
})
export class PaymentCommitmentEndpoint extends AbstractEndpoint {

  constructor(httpClient: HttpClient, authService: AuthService) {
    super(httpClient, authService, '/rest-api/v1/admin/payment-commitment');
  }

  getAllPaymentCommitmentByCampRegistrationsEditionId(campRegistrationsEditionId: string | number) {
    return this.httpClient.get<PaymentCommitmentReadModel[]>(`${this.callsBaseUrl}?campRegistrationsEditionId=${campRegistrationsEditionId}`);
  }

  payForDownPayment(request: PayCommitmentRequest) {
    return this.httpClient.post(`${this.callsBaseUrl}/down-payment/payment`, request);
  }

  payForCampParticipation(request: PayCommitmentRequest) {
    return this.httpClient.post(`${this.callsBaseUrl}/camp-participation/payment`, request);
  }

  payForCampBus(request: PayCommitmentRequest) {
    return this.httpClient.post(`${this.callsBaseUrl}/camp-bus/payment`, request);
  }
}

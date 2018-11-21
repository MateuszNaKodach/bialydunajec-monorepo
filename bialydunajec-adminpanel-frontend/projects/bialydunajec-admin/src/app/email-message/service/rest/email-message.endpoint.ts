import {Injectable} from '@angular/core';
import {AbstractEndpoint} from '../../../shared/service/rest/abstract.endpoint';
import {HttpClient} from '@angular/common/http';
import {AuthService} from '../../../auth/service/auth.service';
import {EmailMessageReadModel} from './read-model/email-message.read-model';
import {EmailStatisticsReadModel} from './read-model/email-statistics.read-model';
import {ForwardEmailMessageRequest} from './request/forward-email-message.request';

@Injectable({
  providedIn: 'root'
})
export class EmailMessageEndpoint extends AbstractEndpoint {

  constructor(httpClient: HttpClient, authService: AuthService) {
    super(httpClient, authService, '/rest-api/v1/admin/email-message');
  }

  getAllEmailMessage() {
    return this.httpClient.get<EmailMessageReadModel[]>(this.callsBaseUrl);
  }

  getEmailMessagesStatistics() {
    return this.httpClient.get<EmailStatisticsReadModel>(`${this.callsBaseUrl}/statistics`);
  }

  resendEmailMessage(emailMessageLogId: string) {
    return this.httpClient.put(`${this.callsBaseUrl}/${emailMessageLogId}/resend`, {});
  }

  forwardEmailMessage(emailMessageLogId: string, requestBody: ForwardEmailMessageRequest) {
    return this.httpClient.post(`${this.callsBaseUrl}/${emailMessageLogId}/forward`, requestBody);
  }
}

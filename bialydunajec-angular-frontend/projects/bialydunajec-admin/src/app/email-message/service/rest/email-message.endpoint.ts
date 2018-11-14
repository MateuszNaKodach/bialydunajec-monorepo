import {Injectable} from '@angular/core';
import {AbstractEndpoint} from '../../../shared/service/rest/abstract.endpoint';
import {HttpClient} from '@angular/common/http';
import {AuthService} from '../../../auth/service/auth.service';
import {CreateCampEditionRequest} from '../../../camp-edition/service/rest/request/create-camp-edition.request';
import {UpdateCampEditionDurationRequest} from '../../../camp-edition/service/rest/request/update-camp-edition-duration.request';
import {CampEditionResponse} from '../../../camp-edition/service/rest/response/camp-edition.response';
import {EmailMessageReadModel} from './read-model/email-message.read-model';
import {EmailStatisticsReadModel} from './read-model/email-statistics.read-model';

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
}

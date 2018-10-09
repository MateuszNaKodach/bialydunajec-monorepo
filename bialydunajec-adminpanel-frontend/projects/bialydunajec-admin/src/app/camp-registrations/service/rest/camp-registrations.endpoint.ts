import {AbstractEndpoint} from '../../../shared/service/rest/abstract.endpoint';
import {HttpClient} from '@angular/common/http';
import {AuthService} from '../../../auth/service/auth.service';
import {CampEditionResponse} from '../../../camp-edition/service/rest/response/camp-edition.response';
import {Injectable} from '@angular/core';
import {CampRegistrationsEditionResponse} from './response/camp-registrations-edition.response';
import {UpdateCampRegistrationsTimerRequest} from './request/update-camp-registrations-timer.request';

@Injectable({
  providedIn: 'root'
})
export class CampRegistrationsEndpoint extends AbstractEndpoint {

  constructor(httpClient: HttpClient, authService: AuthService) {
    super(httpClient, authService, '/rest-api/v1/camp-registrations');
  }

  updateCampRegistrationsTimerById(campRegistrationsEditionId: number, requestBody: UpdateCampRegistrationsTimerRequest) {
    return this.httpClient.patch(`${this.callsBaseUrl}/${campRegistrationsEditionId}/timer`, requestBody);
  }

  startCampRegistrationsEditionById(campRegistrationsEditionId: number) {
    return this.httpClient.patch(`${this.callsBaseUrl}/${campRegistrationsEditionId}/start`, null);
  }

  suspendCampRegistrationsEditionById(campRegistrationsEditionId: number) {
    return this.httpClient.patch(`${this.callsBaseUrl}/${campRegistrationsEditionId}/suspend`, null);
  }

  unsuspendCampRegistrationsEditionById(campRegistrationsEditionId: number) {
    return this.httpClient.patch(`${this.callsBaseUrl}/${campRegistrationsEditionId}/unsuspend`, null);
  }

  finishCampRegistrationsEditionById(campRegistrationsEditionId: number) {
    return this.httpClient.patch(`${this.callsBaseUrl}/${campRegistrationsEditionId}/finish`, null);
  }

  getAllCampRegistrations() {
    return this.httpClient.get<CampRegistrationsEditionResponse[]>(`${this.callsBaseUrl}`);
  }

  getCampRegistrationsEditionById(campRegistrationsEditionId: number) {
    return this.httpClient.get<CampRegistrationsEditionResponse[]>(`${this.callsBaseUrl}/${campRegistrationsEditionId}`);
  }

  getAllCampEditions() {
    return this.httpClient.get<CampEditionResponse[]>(`${this.callsBaseUrl}/camp-edition`);
  }

  getCampEditionByCampRegistrationsEditionId(campRegistrationsEditionId: number) {
    return this.httpClient.get<CampEditionResponse>(`${this.callsBaseUrl}/${campRegistrationsEditionId}/camp-edition`);
  }
}

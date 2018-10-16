import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AuthService} from '../../../../../../bialydunajec-admin/src/app/auth/service/auth.service';
import {environment} from '../../../../../../bialydunajec-admin/src/environments/environment';
import {CampRegistrationsResponse} from '../../../../../../bialydunajec-admin/src/app/camp-registrations/service/rest/response/camp-registrations.response';
import {CottageResponse} from '../../../../../../bialydunajec-admin/src/app/camp-registrations/service/rest/response/cottage.response';
import {CampRegistrationsEditionResponse} from '../../../../../../bialydunajec-admin/src/app/camp-registrations/service/rest/response/camp-registrations-edition.response';
import {CampParticipantRegistrationRequest} from './request/camp-participant-registration.request';
import {Gender} from '../../../shared/model/gender.enum';
import {CampRegistrationsCottageResponse} from './response/camp-registrations-cottage.response';

@Injectable({
  providedIn: 'root'
})
export class InProgressCampRegistrationsEndpoint {

  private readonly callsBaseUrl: string;

  constructor(
    protected httpClient: HttpClient
  ) {
    this.callsBaseUrl = environment.restApi.baseUrl + '/rest-api/v1/camp-registrations/in-progress';
  }

  registerCampParticipant(campRegistrationsEditionId: number, requestBody: CampParticipantRegistrationRequest) {
    return this.httpClient.post<CampRegistrationsEditionResponse>(`${this.callsBaseUrl}/camp-participant`, requestBody);
  }

  getInProgressCampRegistrationsEdition() {
    return this.httpClient.get<CampRegistrationsEditionResponse>(this.callsBaseUrl);
  }

  getAllCottagesByInProgressCampRegistrations(camperGender: Gender) {
    return this.httpClient.get<CampRegistrationsCottageResponse[]>(`${this.callsBaseUrl}/cottage?camperGender=${camperGender}`);
  }
}

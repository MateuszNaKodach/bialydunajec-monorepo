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
export class CampRegistrationsEndpoint {

  private readonly callsBaseUrl: string;

  constructor(
    protected httpClient: HttpClient
  ) {
    this.callsBaseUrl = environment.restApi.baseUrl + '/rest-api/v1/camp-registrations';
  }

  registerCampParticipant(campRegistrationsEditionId: number, requestBody: CampParticipantRegistrationRequest) {
    return this.httpClient.post<CampRegistrationsEditionResponse>(`${this.callsBaseUrl}/in-progress/camp-participant`, requestBody);
  }

  verifyCampParticipantRegistration(campParticipantRegistrationId: string, requestBody: {verificationCode: string}) {
    return this.httpClient.patch(`${this.callsBaseUrl}/camp-participant-registration/${campParticipantRegistrationId}/verification`, requestBody);
  }

  getInProgressCampRegistrationsEdition() {
    return this.httpClient.get<CampRegistrationsEditionResponse>(`${this.callsBaseUrl}/in-progress`);
  }

  getAllCottagesByInProgressCampRegistrations(camperGender: Gender) {
    return this.httpClient.get<CampRegistrationsCottageResponse[]>(`${this.callsBaseUrl}/in-progress/cottage?camperGender=${camperGender}`);
  }
}

import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CampRegistrationsEditionResponse} from './response/camp-registrations-edition.response';
import {CampParticipantRegistrationRequest} from './request/camp-participant-registration.request';
import {Gender} from '../../../shared/model/gender.enum';
import {CampRegistrationsCottageResponse} from './response/camp-registrations-cottage.response';
import {CampEditionShirtDto} from './dto/camp-edition-shirt.dto';
import {environment} from '../../../../environments/environment';


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

  getCampEditionShirtByInProgressCampRegistrations() {
    return this.httpClient.get<CampEditionShirtDto>(`${this.callsBaseUrl}/in-progress/camp-shirt`);
  }
}

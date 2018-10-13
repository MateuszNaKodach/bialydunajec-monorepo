import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient, HttpRequest} from '@angular/common/http';
import {AuthService} from '../../../auth/service/auth.service';
import {AbstractEndpoint} from '../../../shared/service/rest/abstract.endpoint';
import {HttpMethod} from '../../../shared/model/HttpMethod.enum';
import {CampEditionResponse} from './response/camp-edition.response';
import {CreateCampEditionRequest} from './request/create-camp-edition.request';
import {UpdateCampEditionDurationRequest} from './request/update-camp-edition-duration.request';

@Injectable({
  providedIn: 'root'
})
export class CampEditionEndpoint extends AbstractEndpoint {

  constructor(httpClient: HttpClient, authService: AuthService) {
    super(httpClient, authService, '/rest-api/v1/camp-edition');
  }

  createCampEdition(requestBody: CreateCampEditionRequest) {
    return this.httpClient.post(this.callsBaseUrl, requestBody);
  }

  updateCampEditionDurationById(campEditionId: number, requestBody: UpdateCampEditionDurationRequest) {
    return this.httpClient.patch(`${this.callsBaseUrl}/${campEditionId}/duration`, requestBody);
  }

  getAllCampEditions() {
    return this.httpClient.get<CampEditionResponse[]>(this.callsBaseUrl);
  }

  getCampEditionById(campEditionId: number) {
    return this.httpClient.get<CampEditionResponse>(`${this.callsBaseUrl}/${campEditionId}`);
  }

  /*
  createCampEdition(requestBody: CreateCampEditionRequest) {
    const request = new HttpRequest(HttpMethod.POST, this.callsBaseUrl, requestBody);
    return this.performAuthenticatedUserRequest(request);
  }

  updateCampEditionDurationById(campEditionId: number, requestBody: UpdateCampEditionDurationRequest) {
    const request = new HttpRequest(HttpMethod.PATCH, `${this.callsBaseUrl}/${campEditionId}/duration`, requestBody);
    return this.performAuthenticatedUserRequest(request);
  }

  getAllCampEditions() {
    const request = new HttpRequest<CampEditionResponse[]>(HttpMethod.GET, this.callsBaseUrl);
    return this.performAuthenticatedUserRequest(request);
  }

  getCampEditionById(campEditionId: number) {
    const request = new HttpRequest<CampEditionResponse>(HttpMethod.GET, `${this.callsBaseUrl}/${campEditionId}`);
    return this.performAuthenticatedUserRequest(request);
  }
*/
}



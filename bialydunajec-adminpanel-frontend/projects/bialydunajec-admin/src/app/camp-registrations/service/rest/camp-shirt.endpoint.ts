import {Injectable} from '@angular/core';
import {AbstractEndpoint} from '../../../shared/service/rest/abstract.endpoint';
import {HttpClient} from '@angular/common/http';
import {AuthService} from '../../../auth/service/auth.service';
import {CampEditionShirtDto} from './dto/camp-edition-shirt.dto';
import {CampEditionShirtSizeRequest} from './request/camp-edition-shirt-size.request';
import {CampEditionShirtColorRequest} from './request/camp-edition-shirt-color.request';
import {environment} from '../../../../environments/environment';
import {ShirtOrderReadModel} from './response/shirt-order.read-model';

@Injectable({
  providedIn: 'root'
})
export class CampShirtEndpoint extends AbstractEndpoint {

  constructor(httpClient: HttpClient, authService: AuthService) {
    super(httpClient, authService, '/rest-api/v1/admin/camp-registrations/camp-shirt');
  }

  getCampEditionShirt(campRegistrationsEditionId: string | number) {
    return this.httpClient.get<CampEditionShirtDto>(`${this.callsBaseUrl}/?campRegistrationsEditionId=${campRegistrationsEditionId}`);
  }

  addCampEditionShirtSizeOption(campEditionShirtId: string, requestBody: CampEditionShirtSizeRequest) {
    return this.httpClient.post(`${this.callsBaseUrl}/${campEditionShirtId}/size`, requestBody);
  }

  updateCampEditionShirtSizeOption(campEditionShirtId: string, shirtSizeOptionId: string, requestBody: CampEditionShirtSizeRequest) {
    return this.httpClient.put(`${this.callsBaseUrl}/${campEditionShirtId}/size/${shirtSizeOptionId}`, requestBody);
  }

  addCampEditionShirtColorOption(campEditionShirtId: string, requestBody: CampEditionShirtColorRequest) {
    return this.httpClient.post(`${this.callsBaseUrl}/${campEditionShirtId}/color`, requestBody);
  }

  updateCampEditionShirtColorOption(campEditionShirtId: string, shirtColorOptionId: string, requestBody: CampEditionShirtColorRequest) {
    return this.httpClient.put(`${this.callsBaseUrl}/${campEditionShirtId}/color/${shirtColorOptionId}`, requestBody);
  }

}

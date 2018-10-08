import {AbstractEndpoint} from '../../../shared/service/rest/abstract.endpoint';
import {HttpClient} from '@angular/common/http';
import {AuthService} from '../../../auth/service/auth.service';
import {CampEditionResponse} from '../../../camp-edition/service/rest/response/camp-edition.response';
import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CampRegistrationsEndpoint extends AbstractEndpoint {
  constructor(httpClient: HttpClient, authService: AuthService) {
    super(httpClient, authService, '/rest-api/v1/camp-registrations');
  }

  getAllCampEditions() {
    return this.httpClient.get<CampEditionResponse[]>(`${this.callsBaseUrl}/camp-edition`);
  }
}

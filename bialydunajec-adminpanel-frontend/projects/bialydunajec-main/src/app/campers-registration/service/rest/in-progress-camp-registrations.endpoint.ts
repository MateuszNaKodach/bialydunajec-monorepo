import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AuthService} from '../../../../../../bialydunajec-admin/src/app/auth/service/auth.service';
import {environment} from '../../../../../../bialydunajec-admin/src/environments/environment';
import {CampRegistrationsResponse} from '../../../../../../bialydunajec-admin/src/app/camp-registrations/service/rest/response/camp-registrations.response';
import {CottageResponse} from '../../../../../../bialydunajec-admin/src/app/camp-registrations/service/rest/response/cottage.response';
import {CampRegistrationsEditionResponse} from '../../../../../../bialydunajec-admin/src/app/camp-registrations/service/rest/response/camp-registrations-edition.response';

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

  getInProgressCampRegistrationsEdition() {
    return this.httpClient.get<CampRegistrationsEditionResponse>(this.callsBaseUrl);
  }

  getAllCottagesByInProgressCampRegistrations() {
    return this.httpClient.get<CottageResponse[]>(this.callsBaseUrl);
  }
}

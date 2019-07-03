import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CottageInfoDto} from './dto/cottage-info.dto';
import {environment} from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CottageEndpoint {

  private readonly callsBaseUrl: string;

  constructor(
    protected httpClient: HttpClient
  ) {
    this.callsBaseUrl = environment.restApi.baseUrl + '/rest-api/v1/camp-registrations/cottage';
  }

  getNewestCottageByAcademicMinistryId(academicMinistryId: string) {
    return this.httpClient.get<CottageInfoDto>(`${this.callsBaseUrl}/newest?academicMinistryId=${academicMinistryId}`);
  }

}

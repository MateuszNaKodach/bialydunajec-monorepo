import {AbstractEndpoint} from '../../../shared/service/rest/abstract.endpoint';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AuthService} from '../../../auth/service/auth.service';
import {CreateAcademicMinistryRequest, UpdateAcademicMinistryRequest} from './request/create-academic-ministry.request';
import {AcademicMinistryResponse} from './response/academic-ministry.response';

@Injectable({
  providedIn: 'root'
})
export class AcademicMinistryAdminEndpoint extends AbstractEndpoint {

  constructor(httpClient: HttpClient, authService: AuthService) {
    super(httpClient, authService, '/rest-api/v1/admin/academic-ministry');
  }

  createAcademicMinistry(requestBody: CreateAcademicMinistryRequest) {
    if (requestBody.description && requestBody.description.content == null && requestBody.description.title == null) {
      requestBody.description = null;
    }
    return this.httpClient.post(this.callsBaseUrl, requestBody);
  }

  updateAcademicMinistryById(academicMinistryId: string, requestBody: UpdateAcademicMinistryRequest) {
    return this.httpClient.put(`${this.callsBaseUrl}/${academicMinistryId}`, requestBody);
  }

  getAllAcademicMinistries() {
    return this.httpClient.get<AcademicMinistryResponse[]>(this.callsBaseUrl);
  }

  getAcademicMinistryById(academicMinistryId: string) {
    return this.httpClient.get<AcademicMinistryResponse>(`${this.callsBaseUrl}/${academicMinistryId}`);
  }
}

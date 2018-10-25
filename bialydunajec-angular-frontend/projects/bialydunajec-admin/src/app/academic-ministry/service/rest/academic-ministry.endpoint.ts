import {AbstractEndpoint} from '../../../shared/service/rest/abstract.endpoint';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AuthService} from '../../../auth/service/auth.service';
import {CreateAcademicMinistryRequest, UpdateAcademicMinistryRequest} from './request/create-academic-ministry.request';
import {AcademicMinistryResponse} from './response/academic-ministry.response';
import {CreateAcademicPriestRequest} from './request/create-academic-priest.request';
import {AcademicPriestDto} from './dto/academic-priest.dto';

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

  createAcademicPriest(academicMinistryId: string, requestBody: CreateAcademicPriestRequest) {
    return this.httpClient.post(`${this.callsBaseUrl}/${academicMinistryId}/priest`, requestBody);
  }

  getAllAcademicPriestByAcademicMinistryId(academicMinistryId: string) {
    return this.httpClient.get<AcademicPriestDto[]>(`${this.callsBaseUrl}/${academicMinistryId}/priest`);
  }

  removeAcademicPriest(academicMinistryId: string, academicPriestId: string) {
    return this.httpClient.delete(`${this.callsBaseUrl}/${academicMinistryId}/priest/${academicPriestId}`);
  }
}

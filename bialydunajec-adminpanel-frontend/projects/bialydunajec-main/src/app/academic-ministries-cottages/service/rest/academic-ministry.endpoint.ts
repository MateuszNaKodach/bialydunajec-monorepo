import {Injectable} from '@angular/core';
import {AbstractEndpoint} from '../../../../../../bialydunajec-admin/src/app/shared/service/rest/abstract.endpoint';
import {HttpClient} from '@angular/common/http';
import {AuthService} from '../../../../../../bialydunajec-admin/src/app/auth/service/auth.service';
import {AcademicMinistryResponse} from '../../../../../../bialydunajec-admin/src/app/academic-ministry/service/rest/response/academic-ministry.response';
import {AcademicMinistryNameResponse} from './dto/academic-ministry-name.response';
import {AcademicPriestDto} from '../../../../../../bialydunajec-admin/src/app/academic-ministry/service/rest/dto/academic-priest.dto';

@Injectable({
  providedIn: 'root'
})
export class AcademicMinistryEndpoint extends AbstractEndpoint {

  constructor(httpClient: HttpClient, authService: AuthService) {
    super(httpClient, authService, '/rest-api/v1/academic-ministry');
  }

  getAllAcademicMinistries() {
    return this.httpClient.get<AcademicMinistryResponse[]>(this.callsBaseUrl);
  }

  getAllAcademicMinistriesNames() {
    return this.httpClient.get<AcademicMinistryNameResponse[]>(`${this.callsBaseUrl}/name`);
  }

  getAcademicMinistryById(academicMinistryId: string) {
    return this.httpClient.get<AcademicMinistryResponse>(`${this.callsBaseUrl}/${academicMinistryId}`);
  }

  getAllAcademicPriestByAcademicMinistryId(academicMinistryId: string) {
    return this.httpClient.get<AcademicPriestDto[]>(`${this.callsBaseUrl}/${academicMinistryId}/priest`);
  }
}

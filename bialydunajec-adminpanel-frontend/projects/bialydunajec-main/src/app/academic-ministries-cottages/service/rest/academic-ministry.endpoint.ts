import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AcademicMinistryResponse} from '../../../../../../bialydunajec-admin/src/app/academic-ministry/service/rest/response/academic-ministry.response';
import {AcademicMinistryNameResponse} from './dto/academic-ministry-name.response';
import {AcademicPriestDto} from '../../../../../../bialydunajec-admin/src/app/academic-ministry/service/rest/dto/academic-priest.dto';
import {environment} from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AcademicMinistryEndpoint {

  private readonly callsBaseUrl: string;

  constructor(
    protected httpClient: HttpClient
  ) {
    this.callsBaseUrl = environment.restApi.baseUrl + '/rest-api/v1/academic-ministry';
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

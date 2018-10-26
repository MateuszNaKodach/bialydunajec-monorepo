import {Injectable} from '@angular/core';
import {AbstractEndpoint} from '../../../../../../bialydunajec-admin/src/app/shared/service/rest/abstract.endpoint';
import {HttpClient} from '@angular/common/http';
import {AuthService} from '../../../../../../bialydunajec-admin/src/app/auth/service/auth.service';
import {CottageInfoDto} from './dto/cottage-info.dto';

@Injectable({
  providedIn: 'root'
})
export class CottageEndpoint extends AbstractEndpoint {

  constructor(httpClient: HttpClient, authService: AuthService) {
    super(httpClient, authService, '/rest-api/v1/camp-registrations/cottage');
  }

  getNewestCottageByAcademicMinistryId(academicMinistryId: string) {
    return this.httpClient.get<CottageInfoDto>(`${this.callsBaseUrl}/newest?academicMinistryId=${academicMinistryId}`);
  }

}

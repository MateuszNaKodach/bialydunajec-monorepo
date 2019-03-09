import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CampNewsPageDto} from './dto/camp-news-page.dto';
import {environment} from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CampNewsEndpoint {

  private readonly callsBaseUrl: string;

  constructor(
    protected httpClient: HttpClient
  ) {
    this.callsBaseUrl = environment.restApi.baseUrl + '/rest-api/v1/camp-news';
  }

  getLastCampNews() {
    return this.httpClient.get<CampNewsPageDto>(this.callsBaseUrl);
  }

}

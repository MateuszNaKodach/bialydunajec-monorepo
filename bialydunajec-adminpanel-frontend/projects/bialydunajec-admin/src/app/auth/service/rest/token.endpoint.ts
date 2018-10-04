import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../environments/environment';
import {TokenResponse} from './response/token.response';

@Injectable({
  providedIn: 'root'
})
export class TokenEndpoint {

  private callsBaseUrl = environment.restApi.baseUrl + '/oauth/token';
  private baseAuthorization = {
    username: environment.restApi.clientId,
    password: environment.restApi.clientSecret
  };

  constructor(private httpClient: HttpClient) {
  }

  postOAuthToken(usernameOrEmail: string, password: string) {
    return this.httpClient.post<TokenResponse>(
      this.callsBaseUrl,
      null,
      {
        params: {
          username: usernameOrEmail,
          password: password,
          grant_type: 'password'
        },
        headers: {
          'Authorization': 'Basic ' + btoa(this.baseAuthorization.username + ':' + this.baseAuthorization.password),
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      }
    );
  }

  simplePost() {
    return this.httpClient.post('http://localhost:3536/rest-api/v1/camp-edition', {
      campEditionId: 40,
      campEditionStartDate: '2018-01-01',
      campEditionEndDate: '2018-01-02'
    });
  }

  simpleGet() {
    return this.httpClient.get('http://localhost:3536/rest-api/v1/camp-edition');
  }
}

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
}

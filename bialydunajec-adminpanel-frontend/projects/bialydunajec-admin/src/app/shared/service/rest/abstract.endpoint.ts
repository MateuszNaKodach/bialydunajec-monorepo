import {HttpClient, HttpRequest} from '@angular/common/http';
import {AuthService} from '../../../auth/service/auth.service';
import {environment} from '../../../../environments/environment';
import {HttpHeader} from '../../model/HttpHeader.enum';

export class AbstractEndpoint {

  protected callsBaseUrl: string;

  constructor(
    protected httpClient: HttpClient,
    protected authService: AuthService,
    callsBaseUrl: string
  ) {
    this.callsBaseUrl = environment.restApi.baseUrl + callsBaseUrl;
  }

  protected performAuthenticatedUserRequest(httpRequest: HttpRequest<any>) {
    const {access_token, token_type} = this.authService.userOAuthToken;
    httpRequest.headers[HttpHeader.AUTHORIZATION] = token_type + ' ' + access_token;
    console.log(httpRequest.headers);
    return this.httpClient.request(httpRequest);
  }

  protected performRequest(httpRequest: HttpRequest<any>) {
    return this.httpClient.request(httpRequest);
  }

  protected getUserAuthorizationHeader() {
    const {access_token, token_type} = this.authService.userOAuthToken;
    if (access_token) {
      return {'Authorization': token_type + ' ' + access_token};
    } else {
      return null;
    }
  }

}

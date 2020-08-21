import {HttpResponseBase} from '@angular/common/http';

export class HttpResponseHelper {
  static isStatus4xx(httpResponse: HttpResponseBase) {
    return httpResponse.status >= 400 && httpResponse.status < 500;
  }
}

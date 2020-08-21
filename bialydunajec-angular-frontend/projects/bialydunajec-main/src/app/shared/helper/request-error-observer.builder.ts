import {RestErrorCode} from '../../campers-registration/service/rest/response/rest-error.code';
import {HttpErrorResponse} from '@angular/common/http';
import {HttpResponseHelper} from './HttpResponseHelper';

export class RequestErrorObserverBuilder {
  private readonly restError: (restErrors: string[] | RestErrorCode[]) => any;
  private readonly networkError: (error) => any;
  private readonly unhandledError: (error) => any;

  private static defaultCallback = error => console.log(error);

  constructor(
    restError: (restErrors: string[] | RestErrorCode[]) => any = RequestErrorObserverBuilder.defaultCallback,
    unhandledError: (error) => any = RequestErrorObserverBuilder.defaultCallback,
    networkError: (error) => any = RequestErrorObserverBuilder.defaultCallback) {
    this.restError = restError;
    this.networkError = networkError;
    this.unhandledError = unhandledError;
  }

  static anyError(callback: (error) => any) {
    return new RequestErrorObserverBuilder(callback, callback, callback);
  }

  getRequestErrorObserver() {
    return (response: HttpErrorResponse) => {
      const error = response.error;
      const restErrors = response.error.restErrors;
      if (HttpResponseHelper.isStatus4xx(response) && restErrors) {
        console.log('REST ERROR');
        this.restError(restErrors);
      } else if (response.status === 0) {
        console.log('NETWORK ERROR');
        this.networkError(error);
      } else {
        console.log('UNHANDLED ERROR');
        this.unhandledError(error);
      }
    };
  }
}





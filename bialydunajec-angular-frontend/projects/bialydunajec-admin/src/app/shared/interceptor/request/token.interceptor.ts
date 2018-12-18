import {Injectable} from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import {AuthService} from '../../../auth/service/auth.service';
import {Observable} from 'rxjs';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(public authService: AuthService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.authService.isAuthenticated()) {
      request = request.clone({
        setHeaders: {
          Authorization: `bearer ${this.authService.userOAuthToken.access_token}`
        }
      });
    }
    return next.handle(request);
  }
}

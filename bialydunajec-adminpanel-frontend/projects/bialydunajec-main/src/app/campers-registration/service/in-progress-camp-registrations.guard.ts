import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, CanActivateChild, Router, RouterStateSnapshot} from '@angular/router';
import {AuthService} from '../../../../../bialydunajec-admin/src/app/auth/service/auth.service';
import {Observable} from 'rxjs';
import {InProgressCampRegistrationsEndpoint} from './rest/in-progress-camp-registrations.endpoint';
import {map, tap} from 'rxjs/operators';
import {appRoutingPaths} from '../../app-routing.paths';
import {campersRegistrationRoutingPaths} from '../campers-registration-routing.paths';

@Injectable({
  providedIn: 'root'
})
export class InProgressCampRegistrationsGuard implements CanActivate, CanActivateChild {

  constructor(private inProgressCampRegistrationsEndpoint: InProgressCampRegistrationsEndpoint, private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    return this.inProgressCampRegistrationsEndpoint.getInProgressCampRegistrationsEdition()
      .pipe(
        map(campRegistrations => campRegistrations != null),
        tap(existsInProgressCampRegistrations => {
          if (!existsInProgressCampRegistrations) {
            this.router.navigate([appRoutingPaths.campersRegistration, campersRegistrationRoutingPaths.start]);
          }
        })
      );
  }

  canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    return this.canActivate(childRoute, state);
  }

}

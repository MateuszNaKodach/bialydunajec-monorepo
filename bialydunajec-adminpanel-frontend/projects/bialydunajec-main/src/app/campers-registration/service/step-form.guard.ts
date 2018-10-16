import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, CanActivateChild, Router, RouterStateSnapshot} from '@angular/router';
import {InProgressCampRegistrationsEndpoint} from './rest/in-progress-camp-registrations.endpoint';
import {Observable} from 'rxjs';
import {map, tap} from 'rxjs/operators';
import {appRoutingPaths} from '../../app-routing.paths';
import {campersRegistrationRoutingPaths} from '../campers-registration-routing.paths';
import {CamperRegistrationFormStateService} from './camper-registration-form-state.service';
import {RegistrationFormConfig} from '../component/registration-form/registration-form.config';
import {CamperRegistrationFormNavigator} from './camper-registration-form.navigator';

@Injectable({
  providedIn: 'root'
})
export class InProgressCampRegistrationsGuard implements CanActivateChild {

  constructor(private camperRegistrationFormStateService: CamperRegistrationFormStateService,
              private router: Router,
              private formNavigator: CamperRegistrationFormNavigator) {
  }

  canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    return false;
  }

}

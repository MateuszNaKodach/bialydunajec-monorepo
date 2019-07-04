import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../service/auth.service';
import {AngularFormHelper} from '../../../shared/helper/angular-form.helper';
import {Router} from '@angular/router';
import {environment} from '../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {finalize} from 'rxjs/operators';

@Component({
  selector: 'bda-admin-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.less']
})
export class LoginFormComponent implements OnInit, OnDestroy {

  environment = environment;
  authInProgress = false;
  loginErrorMessage: string;
  loginForm: FormGroup;
  private userAuthenticationSubscription;

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router, private httpClient: HttpClient) {
  }

  ngOnInit() {
    this.initForm();
    this.userAuthenticationSubscription = this.authService.userAuthentication
      .subscribe(
        authState => {
          console.log(authState);
          this.authInProgress = false;
          this.loginErrorMessage = authState.errorMessage;
          if (authState.currentUser) {
            this.router.navigate(['/panel']);
          }
        }
      );
  }

  private initForm() {
    this.loginForm = this.formBuilder.group({
      userLogin: [null, [Validators.required]],
      password: [null, [Validators.required]],
      remember: [false, []]
    });
  }

  submitForm() {
    AngularFormHelper.markFormGroupDirty(this.loginForm);
    this.authInProgress = true;
    this.authService.login(this.userLoginFormControl.value, this.passwordFormControl.value, this.rememberFormControl.value);
  }

  get userLoginFormControl() {
    return this.loginForm.get('userLogin');
  }

  get passwordFormControl() {
    return this.loginForm.get('password');
  }

  get rememberFormControl() {
    return this.loginForm.get('remember');
  }

  ngOnDestroy() {
    this.userAuthenticationSubscription.unsubscribe();
  }


  isDatabaseInitialized = false;
  databaseInitializatingInProgress = false;

  initDatabase() {
    this.databaseInitializatingInProgress = true;
    this.httpClient.get(`${environment.restApi.baseUrl}/rest-api/v1/development/db-init`)
      .pipe(
        finalize(() => {
          this.databaseInitializatingInProgress = false;
        })
      )
      .subscribe(_ => this.isDatabaseInitialized = true);
  }

}

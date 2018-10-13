import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../service/auth.service';
import {AngularFormHelper} from '../../../../../../bialydunajec-main/src/app/shared/helper/angular-form.helper';
import {Router} from '@angular/router';

@Component({
  selector: 'bda-admin-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.less']
})
export class LoginFormComponent implements OnInit, OnDestroy {

  authInProgress = false;
  loginErrorMessage: string;
  loginForm: FormGroup;
  private userAuthenticationSubscription;

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router) {
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

}

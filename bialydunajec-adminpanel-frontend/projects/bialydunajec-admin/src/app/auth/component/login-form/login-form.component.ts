import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'bda-admin-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.less']
})
export class LoginFormComponent implements OnInit {

  loginForm: FormGroup;

  constructor(private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.initForm();
  }

  private initForm() {
    this.loginForm = this.formBuilder.group({
      userLogin: [null, [Validators.required]],
      password: [null, [Validators.required]],
      remember: [false, []]
    });
  }

  submitForm() {
    console.log('Form value:', this.loginForm.value);
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

}

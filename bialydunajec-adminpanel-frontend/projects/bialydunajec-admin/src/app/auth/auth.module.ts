import {NgModule} from '@angular/core';

import {AuthRoutingModule} from './auth-routing.module';
import {LoginFormComponent} from './component/login-form/login-form.component';
import {SharedModule} from '../shared/shared.module';
import { CurrentUserComponent } from './component/current-user/current-user.component';

@NgModule({
  imports: [
    AuthRoutingModule,
    SharedModule
  ],
  declarations: [LoginFormComponent, CurrentUserComponent],
  exports: [LoginFormComponent, CurrentUserComponent]
})
export class AuthModule {
}

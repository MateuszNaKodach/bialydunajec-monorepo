import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {authRoutingPaths} from './auth-routing.paths';
import {LoginFormComponent} from './component/login-form/login-form.component';

const authRoutes: Routes = [
  {path: authRoutingPaths.root, redirectTo: authRoutingPaths.login, pathMatch: 'full'},
  {path: authRoutingPaths.login, component: LoginFormComponent}
];

@NgModule({
  imports: [RouterModule.forChild(authRoutes)],
  exports: [RouterModule]
})
export class AuthRoutingModule {
}

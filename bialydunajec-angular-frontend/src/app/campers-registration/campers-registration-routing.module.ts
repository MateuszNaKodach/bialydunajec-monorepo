import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {campersRegistrationRoutingPaths} from './campers-registration-routing.paths';
import {RegistrationFormComponent} from './component/registration-form/registration-form.component';

const campersRegistrationRoutes: Routes = [
  {
    path: campersRegistrationRoutingPaths.root,
    redirectTo: campersRegistrationRoutingPaths.form,
    pathMatch: 'full'
  },
  {
    path: campersRegistrationRoutingPaths.form,
    component: RegistrationFormComponent
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(campersRegistrationRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class CampersRegistrationRoutingModule {
}

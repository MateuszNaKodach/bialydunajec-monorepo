import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {campersRegistrationRoutingPaths} from './campers-registration-routing.paths';
import {RegistrationFormComponent} from './component/registration-form/registration-form.component';
import {PersonalDataFormComponent} from './component/registration-form/personal-data-form/personal-data-form.component';
import {TransportFormComponent} from './component/registration-form/transport-form/transport-form.component';
import {ShirtFormComponent} from './component/registration-form/shirt-form/shirt-form.component';
import {CottageFormComponent} from './component/registration-form/cottage-form/cottage-form.component';
import {RegistrationSummaryComponent} from './component/registration-form/registration-summary/registration-summary.component';

const campersRegistrationRoutes: Routes = [
  {
    path: campersRegistrationRoutingPaths.root,
    redirectTo: campersRegistrationRoutingPaths.form,
    pathMatch: 'full'
  },
  {
    path: campersRegistrationRoutingPaths.form,
    component: RegistrationFormComponent,
    children: [
      {path: '', pathMatch: 'full', redirectTo: campersRegistrationRoutingPaths.personalData},
      {path: campersRegistrationRoutingPaths.personalData, component: PersonalDataFormComponent},
      {path: campersRegistrationRoutingPaths.transport, component: TransportFormComponent},
      {path: campersRegistrationRoutingPaths.shirt, component: ShirtFormComponent},
      {path: campersRegistrationRoutingPaths.cottage, component: CottageFormComponent},
      {path: campersRegistrationRoutingPaths.summary, component: RegistrationSummaryComponent}
    ]
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

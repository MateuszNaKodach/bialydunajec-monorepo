import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {campRegistrationsRoutingPaths} from './camp-registrations-routing.paths';
import {CampRegistrationsSettingsComponent} from './component/camp-registrations-settings/camp-registrations-settings.component';

const campRegistrationsRoutes: Routes = [
  {path: campRegistrationsRoutingPaths.root, component: CampRegistrationsSettingsComponent}
];

@NgModule({
  imports: [RouterModule.forChild(campRegistrationsRoutes)],
  exports: [RouterModule]
})
export class CampRegistrationsRoutingModule {
}

import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {campRegistrationsRoutingPaths} from './camp-registrations-routing.paths';
import {CampEditionSettingsComponent} from './component/camp-edition-settings/camp-edition-settings.component';

const campRegistrationsRoutes: Routes = [
  {path: campRegistrationsRoutingPaths.root, component: CampEditionSettingsComponent}
];

@NgModule({
  imports: [RouterModule.forChild(campRegistrationsRoutes)],
  exports: [RouterModule]
})
export class CampRegistrationsRoutingModule {
}

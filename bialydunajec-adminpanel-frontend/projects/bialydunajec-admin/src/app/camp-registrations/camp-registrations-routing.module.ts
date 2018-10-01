import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {campRegistrationsRoutingPaths} from './camp-registrations-routing.paths';
const campRegistrationsRoutes: Routes = [
  // {path: campRegistrationsRoutingPaths.root}
];

@NgModule({
  imports: [RouterModule.forChild(campRegistrationsRoutes)],
  exports: [RouterModule]
})
export class CampRegistrationsRoutingModule {
}

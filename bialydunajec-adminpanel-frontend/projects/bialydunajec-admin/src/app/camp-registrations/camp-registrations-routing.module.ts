import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {campRegistrationsRoutingPaths} from './camp-registrations-routing.paths';
import {CampRegistrationsSettingsComponent} from './component/camp-registrations-settings/camp-registrations-settings.component';
import {CottageListComponent} from './component/cottage-list/cottage-list.component';
import {CottageEditComponent} from './component/cottage-edit/cottage-edit.component';

const campRegistrationsRoutes: Routes = [
  {path: campRegistrationsRoutingPaths.root, component: CampRegistrationsSettingsComponent},
  {path: campRegistrationsRoutingPaths.cottages, component: CottageListComponent},
  {path: campRegistrationsRoutingPaths.newCottage, component: CottageEditComponent}
];

@NgModule({
  imports: [RouterModule.forChild(campRegistrationsRoutes)],
  exports: [RouterModule]
})
export class CampRegistrationsRoutingModule {
}

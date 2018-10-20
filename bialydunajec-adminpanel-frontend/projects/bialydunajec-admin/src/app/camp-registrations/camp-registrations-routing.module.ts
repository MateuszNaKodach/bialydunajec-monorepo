import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {campRegistrationsRoutingPaths} from './camp-registrations-routing.paths';
import {CampRegistrationsSettingsComponent} from './component/camp-registrations-settings/camp-registrations-settings.component';
import {CottageListComponent} from './component/cottage-list/cottage-list.component';
import {CottageEditComponent} from './component/cottage-edit/cottage-edit.component';
import {CampParticipantListComponent} from './component/camp-participant-list/camp-participant-list.component';
import {ShirtSettingsComponent} from './component/shirt-settings/shirt-settings.component';

const campRegistrationsRoutes: Routes = [
  {path: campRegistrationsRoutingPaths.root, component: CampRegistrationsSettingsComponent},
  {path: campRegistrationsRoutingPaths.cottages, component: CottageListComponent},
  {path: campRegistrationsRoutingPaths.campParticipants, component: CampParticipantListComponent},
  {path: campRegistrationsRoutingPaths.shirtSettings, component: ShirtSettingsComponent},
  {
    path: `${campRegistrationsRoutingPaths.cottages}/:cottageId`,
    component: CottageEditComponent
  },
  {
    path: `${campRegistrationsRoutingPaths.cottages}/:cottageId/${campRegistrationsRoutingPaths.editCottage}`,
    component: CottageEditComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(campRegistrationsRoutes)],
  exports: [RouterModule]
})
export class CampRegistrationsRoutingModule {
}

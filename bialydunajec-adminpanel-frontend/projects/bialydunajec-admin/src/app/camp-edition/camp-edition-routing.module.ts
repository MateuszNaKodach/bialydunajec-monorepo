import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {CampEditionSettingsComponent} from '../camp-registrations/component/camp-edition-settings/camp-edition-settings.component';
import {campEditionRoutingPaths} from './camp-edition-routing.paths';
import {CampEditionListComponent} from './component/camp-edition-list/camp-edition-list.component';

const campEditionRoutes: Routes = [
  {
    path: campEditionRoutingPaths.root,
    component: CampEditionListComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(campEditionRoutes)],
  exports: [RouterModule]
})
export class CampEditionRoutingModule {
}

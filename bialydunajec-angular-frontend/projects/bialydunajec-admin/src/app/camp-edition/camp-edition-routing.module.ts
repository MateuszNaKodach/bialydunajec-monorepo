import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {campEditionRoutingPaths} from './camp-edition-routing.paths';
import {CampEditionListComponent} from './component/camp-edition-list/camp-edition-list.component';
import {CampEditionEditComponent} from './component/camp-edition-edit/camp-edition-edit.component';

const campEditionRoutes: Routes = [
  {
    path: campEditionRoutingPaths.root,
    component: CampEditionListComponent
  },
  {
    path: campEditionRoutingPaths.newCampEdition,
    component: CampEditionEditComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(campEditionRoutes)],
  exports: [RouterModule]
})
export class CampEditionRoutingModule {
}

import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {academicMinistryRoutingPaths} from './academic-ministry.routing.paths';
import {AcademicMinistryEditComponent} from './component/academic-ministry-edit/academic-ministry-edit.component';

const academicMinistryRoutes: Routes = [
  {
    path: academicMinistryRoutingPaths.newCampEdition,
    component: AcademicMinistryEditComponent
  },
  {
    path: `:academicMinistryId/${academicMinistryRoutingPaths.editCampEdition}`,
    component: AcademicMinistryEditComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(academicMinistryRoutes)],
  exports: [RouterModule]
})
export class AcademicMinistryRoutingModule {
}

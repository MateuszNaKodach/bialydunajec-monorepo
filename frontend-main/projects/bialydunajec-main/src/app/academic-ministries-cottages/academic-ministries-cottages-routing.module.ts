import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AcademicMinistriesComponent} from './component/academic-ministries/academic-ministries.component';
import {AcademicMinistryInfoComponent} from './component/academic-ministry-info/academic-ministry-info.component';
import {AcademicMinistryCottageComponent} from './component/academic-ministry-cottage/academic-ministry-cottage.component';
import {AcademicMinistryDetailsComponent} from './component/academic-ministry-details/academic-ministry-details.component';
import {academicMinistriesCottagesRoutingPaths} from './academic-ministries-cottages-routing.paths';

const academicMinistriesCottagesRoutes: Routes = [
  {path: academicMinistriesCottagesRoutingPaths.root, component: AcademicMinistriesComponent},
  {
    path: ':' + academicMinistriesCottagesRoutingPaths.academicMinistryId, component: AcademicMinistryInfoComponent, children: [
      {path: '', pathMatch: 'full', redirectTo: academicMinistriesCottagesRoutingPaths.cottage},
      {path: academicMinistriesCottagesRoutingPaths.cottage, component: AcademicMinistryCottageComponent},
      {path: academicMinistriesCottagesRoutingPaths.academicMinistry, component: AcademicMinistryDetailsComponent}
    ]
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(academicMinistriesCottagesRoutes)
  ],
  exports: [RouterModule]
})
export class AcademicMinistriesCottagesRoutingModule {
}

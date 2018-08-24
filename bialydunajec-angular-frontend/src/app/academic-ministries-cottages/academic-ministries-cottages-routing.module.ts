import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AcademicMinistriesComponent} from './component/academic-ministries/academic-ministries.component';
import {AcademicMinistryInfoComponent} from './component/academic-ministry-info/academic-ministry-info.component';
import {AcademicMinistryCottageComponent} from './component/academic-ministry-cottage/academic-ministry-cottage.component';
import {AcademicMinistryDetailsComponent} from './component/academic-ministry-details/academic-ministry-details.component';
import {academicMinistriesCottagesPaths} from './academic-ministries-cottages.paths';

const academicMinistriesCottagesRoutes: Routes = [
  {path: academicMinistriesCottagesPaths.root, component: AcademicMinistriesComponent},
  {
    path: ':' + academicMinistriesCottagesPaths.academicMinistryId, component: AcademicMinistryInfoComponent, children: [
      {path: '', pathMatch: 'full', redirectTo: academicMinistriesCottagesPaths.cottage},
      {path: academicMinistriesCottagesPaths.cottage, component: AcademicMinistryCottageComponent},
      {path: academicMinistriesCottagesPaths.academicMinistry, component: AcademicMinistryDetailsComponent}
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

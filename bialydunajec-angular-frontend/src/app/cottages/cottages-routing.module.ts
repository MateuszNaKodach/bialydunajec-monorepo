import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AcademicMinistriesComponent} from './component/academic-ministries/academic-ministries.component';
import {AcademicMinistryInfoComponent} from './component/academic-ministry-info/academic-ministry-info.component';
import {CottageDetailsComponent} from './component/academic-ministry-cottage/academic-ministry-cottage.component';
import {AcademicMinistryDetailsComponent} from './component/academic-ministry-details/academic-ministry-details.component';
import {cottagesPaths} from './cottages.paths';

const cottagesRoutes: Routes = [
  {path: cottagesPaths.root, component: AcademicMinistriesComponent},
  {
    path: ':' + cottagesPaths.academicMinistryId, component: AcademicMinistryInfoComponent, children: [
      {path: cottagesPaths.cottage, component: CottageDetailsComponent},
      {path: cottagesPaths.academicMinistry, component: AcademicMinistryDetailsComponent}
    ]
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(cottagesRoutes)
  ],
  exports: [RouterModule]
})
export class CottagesRoutingModule {
}

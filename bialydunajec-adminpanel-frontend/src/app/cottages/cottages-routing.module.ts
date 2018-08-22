import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AcademicMinistriesComponent} from './component/academic-ministries/academic-ministries.component';
import {CottageComponent} from './component/cottage/cottage.component';
import {CottageDetailsComponent} from './component/cottage-details/cottage-details.component';
import {CottageMinistryDetailsComponent} from './component/cottage-ministry-details/cottage-ministry-details.component';

const cottagesRoutes: Routes = [
  {path: '', component: AcademicMinistriesComponent},
  {
    path: ':academicMinistryName', component: CottageComponent, children: [
      {path: 'chatka', component: CottageDetailsComponent},
      {path: 'duszpasterstwo', component: CottageMinistryDetailsComponent}
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

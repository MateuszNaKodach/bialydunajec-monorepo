import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AcademicMinistriesComponent} from './academic-ministries/academic-ministries.component';
import {CottageComponent} from './cottage/cottage.component';
import {CottageDetailsComponent} from './cottage/cottage-details/cottage-details.component';
import {CottageMinistryDetailsComponent} from './cottage/cottage-ministry-details/cottage-ministry-details.component';

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

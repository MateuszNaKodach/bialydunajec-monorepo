import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AcademicMinistriesComponent} from './academic-ministries/academic-ministries.component';


const cottagesRoutes: Routes = [{
  path: '', component: AcademicMinistriesComponent
}];

@NgModule({
  imports: [
    RouterModule.forChild(cottagesRoutes)
  ],
  exports: [RouterModule]
})
export class CottagesRoutingModule {
}

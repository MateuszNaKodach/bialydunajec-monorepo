import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';

const campersRegistrationRoutes: Routes = [
  {path: '', component: null}
];

@NgModule({
  imports: [
    RouterModule.forChild(campersRegistrationRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class CampersRegistrationRoutingModule {
}

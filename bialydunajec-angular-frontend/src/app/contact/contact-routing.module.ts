import {RouterModule, Routes} from '@angular/router';
import {CampCadreContactComponent} from './component/camp-cadre-contact/camp-cadre-contact.component';
import {NgModule} from '@angular/core';

const contactRouter: Routes = [
  {path: '', component: CampCadreContactComponent}
];

@NgModule({
  imports: [
    RouterModule.forChild(contactRouter)
  ],
  exports: [
    RouterModule
  ]
})
export class ContactRoutingModule {

}

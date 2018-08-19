import {NgModule} from '@angular/core';
import {CampCadreContactComponent} from './component/camp-cadre-contact/camp-cadre-contact.component';
import {SharedModule} from '../shared/shared.module';
import {ContactRoutingModule} from './contact-routing.module';

@NgModule({
  imports: [
    SharedModule,
    ContactRoutingModule
  ],
  declarations: [CampCadreContactComponent]
})
export class ContactModule {
}

import {NgModule} from '@angular/core';
import {CampCadreContactComponent} from './component/camp-cadre-contact/camp-cadre-contact.component';
import {SharedModule} from '../shared/shared.module';
import {ContactRoutingModule} from './contact-routing.module';
import {CampCadreService} from './service/camp-cadre.service';
import {FlexLayoutModule} from '@angular/flex-layout';
import { CampCadreMemberComponent } from './component/camp-cadre-member/camp-cadre-member.component';

@NgModule({
  imports: [
    SharedModule,
    ContactRoutingModule,
    FlexLayoutModule
  ],
  declarations: [CampCadreContactComponent, CampCadreMemberComponent],
  providers: [CampCadreService]
})
export class ContactModule {
}

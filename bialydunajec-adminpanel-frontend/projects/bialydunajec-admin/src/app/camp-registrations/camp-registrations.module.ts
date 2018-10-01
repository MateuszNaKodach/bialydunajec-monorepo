import {NgModule} from '@angular/core';

import {SharedModule} from '../shared/shared.module';
import {CampRegistrationsRoutingModule} from './camp-registrations-routing.module';
import { CampEditionSettingsComponent } from './component/camp-edition-settings/camp-edition-settings.component';

@NgModule({
  imports: [
    CampRegistrationsRoutingModule,
    SharedModule
  ],
  declarations: [CampEditionSettingsComponent],
  exports: []
})
export class CampRegistrationsModule {
}

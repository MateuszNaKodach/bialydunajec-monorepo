import {NgModule} from '@angular/core';

import {SharedModule} from '../shared/shared.module';
import {CampRegistrationsRoutingModule} from './camp-registrations-routing.module';
import {CampRegistrationsSettingsComponent} from './component/camp-registrations-settings/camp-registrations-settings.component';

@NgModule({
  imports: [
    CampRegistrationsRoutingModule,
    SharedModule
  ],
  declarations: [CampRegistrationsSettingsComponent],
  exports: []
})
export class CampRegistrationsModule {
}

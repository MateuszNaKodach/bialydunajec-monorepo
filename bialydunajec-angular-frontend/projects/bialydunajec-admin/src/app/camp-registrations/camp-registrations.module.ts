import {NgModule} from '@angular/core';

import {SharedModule} from '../shared/shared.module';
import {CampRegistrationsRoutingModule} from './camp-registrations-routing.module';
import {CampRegistrationsSettingsComponent} from './component/camp-registrations-settings/camp-registrations-settings.component';
import { CottageListComponent } from './component/cottage-list/cottage-list.component';
import { CottageEditComponent } from './component/cottage-edit/cottage-edit.component';
import { NewCottageCardComponent } from './component/cottage-list/new-cottage-card/new-cottage-card.component';
import { CottageCardComponent } from './component/cottage-list/cottage-card/cottage-card.component';

@NgModule({
  imports: [
    CampRegistrationsRoutingModule,
    SharedModule
  ],
  declarations: [CampRegistrationsSettingsComponent, CottageListComponent, CottageEditComponent, NewCottageCardComponent, CottageCardComponent],
  exports: []
})
export class CampRegistrationsModule {
}

import {NgModule} from '@angular/core';

import {SharedModule} from '../shared/shared.module';
import {CampRegistrationsRoutingModule} from './camp-registrations-routing.module';
import {CampRegistrationsSettingsComponent} from './component/camp-registrations-settings/camp-registrations-settings.component';
import {CottageListComponent} from './component/cottage-list/cottage-list.component';
import {CottageEditComponent} from './component/cottage-edit/cottage-edit.component';
import {NewCottageCardComponent} from './component/cottage-list/new-cottage-card/new-cottage-card.component';
import {CottageCardComponent} from './component/cottage-list/cottage-card/cottage-card.component';
import {CottageStatusBadgeComponent} from './component/cottage-status-badge/cottage-status-badge.component';
import {CampParticipantListComponent} from './component/camp-participant-list/camp-participant-list.component';
import { ShirtSettingsComponent } from './component/shirt-settings/shirt-settings.component';
import { ShirtOrdersComponent } from './component/shirt-orders/shirt-orders.component';
import { CampRegistrationsStatisticsComponent } from './component/camp-registrations-statistics/camp-registrations-statistics.component';

@NgModule({
  imports: [
    CampRegistrationsRoutingModule,
    SharedModule
  ],
  declarations: [
    CampRegistrationsSettingsComponent,
    CottageListComponent,
    CottageEditComponent,
    NewCottageCardComponent,
    CottageCardComponent,
    CottageStatusBadgeComponent,
    CampParticipantListComponent,
    ShirtSettingsComponent,
    ShirtOrdersComponent,
    CampRegistrationsStatisticsComponent
  ],
  exports: []
})
export class CampRegistrationsModule {
}

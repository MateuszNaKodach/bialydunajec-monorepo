import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CampBusRoutingModule } from './camp-bus-routing.module';
import { CampBusSettingsComponent } from './component/camp-bus-settings/camp-bus-settings.component';
import { CampBusReservationsComponent } from './component/camp-bus-reservations/camp-bus-reservations.component';

@NgModule({
  imports: [
    CommonModule,
    CampBusRoutingModule
  ],
  declarations: [CampBusSettingsComponent, CampBusReservationsComponent]
})
export class CampBusModule { }

import {NgModule} from '@angular/core';
import {SharedModule} from '../shared/shared.module';
import {FlexLayoutModule} from '@angular/flex-layout';
import {TransportComponent} from './component/transport/transport.component';
import {TransportRoutingModule} from './transport-routing.module';
import {MeanOfTransportComponent} from './component/mean-of-transport/mean-of-transport.component';
import {MeanOfTransportHeaderComponent} from './component/mean-of-transport-header/mean-of-transport-header.component';
import {TransportInfoCampBusComponent} from './component/transport-info-camp-bus/transport-info-camp-bus.component';
import {TransportInfoCarComponent} from './component/transport-info-car/transport-info-car.component';
import {TransportInfoHitchHikingComponent} from './component/transport-info-hitch-hiking/transport-info-hitch-hiking.component';

@NgModule({
  imports: [
    SharedModule,
    TransportRoutingModule,
    SharedModule
  ],
  declarations: [
    TransportComponent,
    MeanOfTransportComponent,
    MeanOfTransportHeaderComponent,
    TransportInfoCampBusComponent,
    TransportInfoCarComponent,
    TransportInfoHitchHikingComponent
  ]
})
export class TransportModule {
}

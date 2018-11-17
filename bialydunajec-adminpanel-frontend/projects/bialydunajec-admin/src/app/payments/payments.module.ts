import { NgModule } from '@angular/core';

import { PaymentsRoutingModule } from './payments-routing.module';
import { PaymentsListComponent } from './component/payments-list/payments-list.component';
import {SharedModule} from '../shared/shared.module';

@NgModule({
  imports: [
    SharedModule,
    PaymentsRoutingModule
  ],
  declarations: [PaymentsListComponent]
})
export class PaymentsModule { }

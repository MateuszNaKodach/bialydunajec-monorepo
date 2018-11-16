import { NgModule } from '@angular/core';

import { EmailMessageRoutingModule } from './email-message-routing.module';
import { EmailHistoryComponent } from './component/email-history/email-history.component';
import {SharedModule} from '../shared/shared.module';

@NgModule({
  imports: [
    SharedModule,
    EmailMessageRoutingModule
  ],
  declarations: [EmailHistoryComponent]
})
export class EmailMessageModule { }

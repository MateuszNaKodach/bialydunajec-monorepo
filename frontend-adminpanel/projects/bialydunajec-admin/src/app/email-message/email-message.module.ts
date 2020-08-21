import { NgModule } from '@angular/core';

import { EmailMessageRoutingModule } from './email-message-routing.module';
import { EmailHistoryComponent } from './component/email-history/email-history.component';
import {SharedModule} from '../shared/shared.module';
import { CreateMessageComponent } from './component/create-message/create-message.component';
import { EmailComposerComponent } from './component/email-composer/email-composer.component';

@NgModule({
  imports: [
    SharedModule,
    EmailMessageRoutingModule
  ],
  declarations: [EmailHistoryComponent, CreateMessageComponent, EmailComposerComponent]
})
export class EmailMessageModule { }

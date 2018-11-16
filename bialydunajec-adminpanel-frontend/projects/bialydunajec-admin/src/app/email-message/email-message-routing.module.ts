import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {emailMessageRoutingPaths} from './email-message-routing.paths';
import {EmailHistoryComponent} from './component/email-history/email-history.component';

const emailMessageRoutes: Routes = [
  {
    path: emailMessageRoutingPaths.history,
    component: EmailHistoryComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(emailMessageRoutes)],
  exports: [RouterModule]
})
export class EmailMessageRoutingModule {
}

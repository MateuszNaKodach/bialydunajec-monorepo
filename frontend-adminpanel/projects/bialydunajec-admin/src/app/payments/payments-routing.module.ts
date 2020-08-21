import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {paymentsRoutingPaths} from './payments-routing.paths';
import {PaymentsListComponent} from './component/payments-list/payments-list.component';

const routes: Routes = [
  {path: paymentsRoutingPaths.root, component: PaymentsListComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PaymentsRoutingModule { }

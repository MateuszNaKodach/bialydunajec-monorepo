import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TransportComponent} from './component/transport/transport.component';

const transportRoutes: Routes = [
  {path: '', component: TransportComponent}
];

@NgModule({
  imports: [
    RouterModule.forChild(transportRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class TransportRoutingModule {

}

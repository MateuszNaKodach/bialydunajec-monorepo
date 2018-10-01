import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {SharedModule} from '../shared/shared.module';
import {CampRegistrationsRoutingModule} from './camp-registrations-routing.module';

@NgModule({
  imports: [
    CommonModule,
    CampRegistrationsRoutingModule,
    SharedModule
  ],
  declarations: [],
  exports: []
})
export class CampRegistrationsModule {
}

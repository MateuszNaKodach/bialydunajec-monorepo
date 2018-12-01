import {NgModule} from '@angular/core';
import {PanelLayoutComponent} from './component/panel-layout/panel-layout.component';
import {SharedModule} from '../shared/shared.module';
import {CoreRoutingModule} from './core-routing.module';
import { DashboardComponent } from './component/dashboard/dashboard.component';

@NgModule({
  imports: [
    SharedModule,
    CoreRoutingModule
  ],
  declarations: [PanelLayoutComponent, DashboardComponent],
  exports: [PanelLayoutComponent]
})
export class CoreModule {
}

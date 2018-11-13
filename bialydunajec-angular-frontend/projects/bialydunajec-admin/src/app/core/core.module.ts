import {NgModule} from '@angular/core';
import {PanelLayoutComponent} from './component/panel-layout/panel-layout.component';
import {SharedModule} from '../shared/shared.module';
import {CoreRoutingModule} from './core-routing.module';

@NgModule({
  imports: [
    SharedModule,
    CoreRoutingModule
  ],
  declarations: [PanelLayoutComponent],
  exports: [PanelLayoutComponent]
})
export class CoreModule {
}

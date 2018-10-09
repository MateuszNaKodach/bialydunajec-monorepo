import {NgModule} from '@angular/core';
import {PanelLayoutComponent} from './component/panel-layout/panel-layout.component';
import {SharedModule} from '../shared/shared.module';
import {CoreRoutingModule} from './core-routing.module';
import {AuthModule} from '../auth/auth.module';
import {CampRegistrationsModule} from '../camp-registrations/camp-registrations.module';
import {AcademicMinistryModule} from '../academic-ministry/academic-ministry.module';

@NgModule({
  imports: [
    SharedModule,
    CoreRoutingModule,
    AuthModule,
    CampRegistrationsModule,
    AcademicMinistryModule
  ],
  declarations: [PanelLayoutComponent],
  exports: [PanelLayoutComponent]
})
export class CoreModule {
}

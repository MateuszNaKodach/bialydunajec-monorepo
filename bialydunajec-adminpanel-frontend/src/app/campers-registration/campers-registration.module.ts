import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RegistrationFormComponent} from './component/registration-form/registration-form.component';
import {CampersRegistrationRoutingModule} from './campers-registration-routing.module';
import {SharedModule} from '../shared/shared.module';

@NgModule({
  imports: [
    SharedModule,
    CampersRegistrationRoutingModule
  ],
  declarations: [RegistrationFormComponent]
})
export class CampersRegistrationModule {
}

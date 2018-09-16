import {NgModule} from '@angular/core';
import {RegistrationFormComponent} from './component/registration-form/registration-form.component';
import {CampersRegistrationRoutingModule} from './campers-registration-routing.module';
import {SharedModule} from '../shared/shared.module';
import {RegistrationStepperComponent} from './component/registration-stepper/registration-stepper.component';
import {PersonalDataFormComponent} from './component/registration-form/personal-data-form/personal-data-form.component';
import {ShirtFormComponent} from './component/registration-form/shirt-form/shirt-form.component';
import {TransportFormComponent} from './component/registration-form/transport-form/transport-form.component';
import {CottageFormComponent} from './component/registration-form/cottage-form/cottage-form.component';
import {ReactiveFormsModule} from '@angular/forms';
import {SuiCheckboxModule, SuiSelectModule} from 'ng2-semantic-ui';
import { FormNavigationButtonsComponent } from './component/registration-form/form-navigation-buttons/form-navigation-buttons.component';
import { FormInputErrorComponent } from './component/form-input-error/form-input-error.component';

@NgModule({
  imports: [
    SharedModule,
    CampersRegistrationRoutingModule,
    ReactiveFormsModule,
    SuiCheckboxModule,
    SuiSelectModule
  ],
  declarations: [
    RegistrationFormComponent,
    RegistrationStepperComponent,
    PersonalDataFormComponent,
    ShirtFormComponent,
    TransportFormComponent,
    CottageFormComponent,
    FormNavigationButtonsComponent,
    FormInputErrorComponent
  ]
})
export class CampersRegistrationModule {
}

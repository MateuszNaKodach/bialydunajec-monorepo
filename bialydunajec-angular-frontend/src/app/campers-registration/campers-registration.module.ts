import {NgModule} from '@angular/core';
import {RegistrationFormComponent} from './component/registration-form/registration-form.component';
import {CampersRegistrationRoutingModule} from './campers-registration-routing.module';
import {SharedModule} from '../shared/shared.module';
import {RegistrationStepperComponent} from './component/registration-form/registration-stepper/registration-stepper.component';
import {PersonalDataFormComponent} from './component/registration-form/personal-data-form/personal-data-form.component';
import {ShirtFormComponent} from './component/registration-form/shirt-form/shirt-form.component';
import {TransportFormComponent} from './component/registration-form/transport-form/transport-form.component';
import {CottageFormComponent} from './component/registration-form/cottage-form/cottage-form.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {SuiCheckboxModule, SuiSelectModule} from 'ng2-semantic-ui';
import {FormNavigationButtonsComponent} from './component/registration-form/form-navigation-buttons/form-navigation-buttons.component';
import {FormInputComponent} from './component/form-input/form-input.component';
import {CamperRegistrationFormStateService} from './service/camper-registration-form-state.service';
import {CamperRegistrationFormNavigator} from './service/camper-registration-form.navigator';
import { CottageCardComponent } from './component/registration-form/cottage-form/cottage-card/cottage-card.component';

@NgModule({
  imports: [
    SharedModule,
    CampersRegistrationRoutingModule,
    ReactiveFormsModule,
    FormsModule,
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
    FormInputComponent,
    CottageCardComponent
  ],
  providers: [
    CamperRegistrationFormStateService,
    CamperRegistrationFormNavigator
  ]
})
export class CampersRegistrationModule {
}

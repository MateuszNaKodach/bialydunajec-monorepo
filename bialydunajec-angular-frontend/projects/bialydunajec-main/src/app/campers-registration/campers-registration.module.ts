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
import {SuiCheckboxModule, SuiSelectModule, SuiMessageModule, SuiModalModule} from 'ng2-semantic-ui';
import {FormNavigationButtonsComponent} from './component/registration-form/form-navigation-buttons/form-navigation-buttons.component';
import {CamperRegistrationFormStateService} from './service/camper-registration-form-state.service';
import {CamperRegistrationFormNavigator} from './service/camper-registration-form.navigator';
import { CottageCardComponent } from './component/registration-form/cottage-form/cottage-card/cottage-card.component';
import { CottageSelectionComponent } from './component/registration-form/cottage-form/cottage-selection/cottage-selection.component';
import { RegistrationSummaryComponent } from './component/registration-form/registration-summary/registration-summary.component';
import { RegistrationStartComponent } from './component/registration-start/registration-start.component';

@NgModule({
  imports: [
    SharedModule,
    CampersRegistrationRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    SuiCheckboxModule,
    SuiSelectModule,
    SuiMessageModule,
    SuiModalModule
  ],
  declarations: [
    RegistrationFormComponent,
    RegistrationStepperComponent,
    PersonalDataFormComponent,
    ShirtFormComponent,
    TransportFormComponent,
    CottageFormComponent,
    FormNavigationButtonsComponent,
    CottageCardComponent,
    CottageSelectionComponent,
    RegistrationSummaryComponent,
    RegistrationStartComponent
  ],
  providers: [
    CamperRegistrationFormStateService,
    CamperRegistrationFormNavigator
  ]
})
export class CampersRegistrationModule {
}

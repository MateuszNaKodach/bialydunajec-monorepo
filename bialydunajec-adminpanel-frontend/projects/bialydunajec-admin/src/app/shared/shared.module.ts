import {NgModule} from '@angular/core';
import {CommonModule, registerLocaleData} from '@angular/common';
import {NgZorroAntdModule, NZ_I18N, pl_PL} from 'ng-zorro-antd';
import pl from '@angular/common/locales/pl';
import {FormInputComponent} from './component/form-input/form-input.component';
import {BialyDunajecCommonsModule} from 'bialydunajec-commons';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { UserAvatarComponent } from './component/user-avatar/user-avatar.component';
import {CampEditionSelectionComponent} from './component/camp-edition-selection/camp-edition-selection.component';
import {HttpClientModule} from '@angular/common/http';
import { PanelSectionComponent } from './component/panel-section/panel-section.component';
import { BreadcrumbsComponent } from './component/breadcrumbs/breadcrumbs.component';

registerLocaleData(pl);

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    NgZorroAntdModule,
    BialyDunajecCommonsModule
  ],
  declarations: [
    FormInputComponent,
    UserAvatarComponent,
    CampEditionSelectionComponent,
    PanelSectionComponent,
    BreadcrumbsComponent
  ],
  exports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NgZorroAntdModule,
    FormInputComponent,
    UserAvatarComponent,
    BialyDunajecCommonsModule,
    CampEditionSelectionComponent,
    PanelSectionComponent,
    BreadcrumbsComponent
  ],
  providers: [{provide: NZ_I18N, useValue: pl_PL}]
})
export class SharedModule {
}

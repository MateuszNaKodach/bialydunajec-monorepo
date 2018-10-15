import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SectionHeaderComponent} from './component/section-header/section-header.component';
import {DualToggleButtonComponent} from './component/dual-toggle-button/dual-toggle-button.component';
import {CampSignUpButtonComponent} from './component/camp-sign-up-button/camp-sign-up-button.component';
import {FlexLayoutModule} from '@angular/flex-layout';
import {PhotoInfoCardComponent} from './component/photo-info-card/photo-info-card.component';
import {SocialMediaLinksComponent} from './component/social-media-links/social-media-links.component';
import {FormInputComponent} from './component/form-input/form-input.component';
import {HttpClientModule} from '@angular/common/http';
import { CampRegistrationsReminderComponent } from './component/camp-registrations-reminder/camp-registrations-reminder.component';
import {SuiCheckboxModule} from 'ng2-semantic-ui';

@NgModule({
  imports: [
    CommonModule,
    FlexLayoutModule,
    HttpClientModule,
    SuiCheckboxModule
  ],
  declarations: [
    SectionHeaderComponent,
    DualToggleButtonComponent,
    CampSignUpButtonComponent,
    PhotoInfoCardComponent,
    SocialMediaLinksComponent,
    FormInputComponent,
    CampRegistrationsReminderComponent
  ],
  exports: [
    CommonModule,
    FlexLayoutModule,
    SectionHeaderComponent,
    DualToggleButtonComponent,
    CampSignUpButtonComponent,
    PhotoInfoCardComponent,
    SocialMediaLinksComponent,
    FormInputComponent,
    HttpClientModule,
    CampRegistrationsReminderComponent
  ]
})
export class SharedModule {
}

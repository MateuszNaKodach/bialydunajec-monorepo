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
import {RECAPTCHA_SETTINGS, RecaptchaModule, RecaptchaSettings} from 'ng-recaptcha';
import {RecaptchaFormsModule} from 'ng-recaptcha/forms';
import {environment} from '../../environments/environment';

import { AgmCoreModule } from '@agm/core';
import { FacebookPageComponent } from './component/facebook/facebook-page/facebook-page.component';
import {FacebookModule} from 'ngx-facebook';
import {Facebook} from './model/facebook.model';
import {GalleryModule} from '@ngx-gallery/core';
import {LightboxModule} from '@ngx-gallery/lightbox';
import {GallerizeModule} from '@ngx-gallery/gallerize';


@NgModule({
  imports: [
    CommonModule,
    FlexLayoutModule,
    HttpClientModule,
    SuiCheckboxModule,
    RecaptchaModule.forRoot(),
    RecaptchaFormsModule,
    AgmCoreModule.forRoot({apiKey: environment.google.maps.apiKey}),
    FacebookModule.forRoot(),
    GalleryModule.forRoot(),
    LightboxModule.forRoot(),
    GallerizeModule
  ],
  declarations: [
    SectionHeaderComponent,
    DualToggleButtonComponent,
    CampSignUpButtonComponent,
    PhotoInfoCardComponent,
    SocialMediaLinksComponent,
    FormInputComponent,
    CampRegistrationsReminderComponent,
    FacebookPageComponent
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
    CampRegistrationsReminderComponent,
    RecaptchaModule,
    RecaptchaFormsModule,
    AgmCoreModule,
    FacebookModule,
    GalleryModule,
    LightboxModule,
    GallerizeModule
  ],
  providers: [
    {
      provide: RECAPTCHA_SETTINGS,
      useValue: { siteKey: environment.google.reCaptcha.siteKey } as RecaptchaSettings,
    },
  ],
})
export class SharedModule {
}

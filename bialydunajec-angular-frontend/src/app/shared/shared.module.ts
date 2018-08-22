import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SectionHeaderComponent} from './component/section-header/section-header.component';
import {DualToggleButtonComponent} from './component/dual-toggle-button/dual-toggle-button.component';
import {CampSignUpButtonComponent} from './component/camp-sign-up-button/camp-sign-up-button.component';
import {FlexLayoutModule} from '@angular/flex-layout';
import {PhotoInfoCardComponent} from './component/photo-info-card/photo-info-card.component';

@NgModule({
  imports: [
    CommonModule,
    FlexLayoutModule
  ],
  declarations: [SectionHeaderComponent, DualToggleButtonComponent, CampSignUpButtonComponent, PhotoInfoCardComponent],
  exports: [
    CommonModule,
    FlexLayoutModule,
    SectionHeaderComponent,
    DualToggleButtonComponent,
    CampSignUpButtonComponent,
    PhotoInfoCardComponent
  ]
})
export class SharedModule {
}

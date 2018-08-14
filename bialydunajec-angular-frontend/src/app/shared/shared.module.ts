import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SectionHeaderComponent} from './section-header/section-header.component';
import { DualToggleButtonComponent } from './dual-toggle-button/dual-toggle-button.component';
import { CampSignUpButtonComponent } from './camp-sign-up-button/camp-sign-up-button.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [SectionHeaderComponent, DualToggleButtonComponent, CampSignUpButtonComponent],
  exports: [CommonModule, SectionHeaderComponent, DualToggleButtonComponent, CampSignUpButtonComponent]
})
export class SharedModule {
}

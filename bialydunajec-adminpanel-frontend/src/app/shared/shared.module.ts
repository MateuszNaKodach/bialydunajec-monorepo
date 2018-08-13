import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SectionHeaderComponent} from './section-header/section-header.component';
import { DualToggleButtonComponent } from './dual-toggle-button/dual-toggle-button.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [SectionHeaderComponent, DualToggleButtonComponent],
  exports: [CommonModule, SectionHeaderComponent, DualToggleButtonComponent]
})
export class SharedModule {
}

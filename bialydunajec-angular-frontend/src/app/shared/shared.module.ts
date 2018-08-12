import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SectionHeaderComponent} from './section-header/section-header.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [SectionHeaderComponent],
  exports: [CommonModule, SectionHeaderComponent]
})
export class SharedModule {
}

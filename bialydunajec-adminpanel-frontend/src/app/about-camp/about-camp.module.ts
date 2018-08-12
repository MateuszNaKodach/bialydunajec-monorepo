import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FaqComponent } from './faq/faq.component';
import {SharedModule} from '../shared/shared.module';
import {FlexLayoutModule} from '@angular/flex-layout';
import {SuiAccordionModule} from 'ng2-semantic-ui';

@NgModule({
  imports: [
    SharedModule,
    FlexLayoutModule,
    SuiAccordionModule
  ],
  declarations: [FaqComponent]
})
export class AboutCampModule { }

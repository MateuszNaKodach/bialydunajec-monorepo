import { NgModule } from '@angular/core';
import { FaqComponent } from './faq/faq.component';
import {SharedModule} from '../shared/shared.module';
import {FlexLayoutModule} from '@angular/flex-layout';
import {SuiAccordionModule} from 'ng2-semantic-ui';
import { FaqQuestionComponent } from './faq/faq-question/faq-question.component';

@NgModule({
  imports: [
    SharedModule,
    FlexLayoutModule,
    SuiAccordionModule
  ],
  declarations: [FaqComponent, FaqQuestionComponent]
})
export class AboutCampModule { }

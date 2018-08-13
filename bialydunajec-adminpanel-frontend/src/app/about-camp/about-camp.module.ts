import { NgModule } from '@angular/core';
import { FaqComponent } from './faq/faq.component';
import {SharedModule} from '../shared/shared.module';
import {FlexLayoutModule} from '@angular/flex-layout';
import {SuiAccordionModule} from 'ng2-semantic-ui';
import { FaqCategoryComponent } from './faq/faq-category/faq-category.component';

@NgModule({
  imports: [
    SharedModule,
    FlexLayoutModule,
    SuiAccordionModule
  ],
  declarations: [FaqComponent, FaqCategoryComponent]
})
export class AboutCampModule { }

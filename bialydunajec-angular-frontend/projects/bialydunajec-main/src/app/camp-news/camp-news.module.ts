import { NgModule } from '@angular/core';
import { CampNewsRoutingModule } from './camp-news-routing.module';
import { CampNewsListComponent } from './component/camp-news-list/camp-news-list.component';
import {SharedModule} from '../shared/shared.module';
import { CampNewsItemComponent } from './component/camp-news-list/camp-news-item/camp-news-item.component';

@NgModule({
  imports: [
    SharedModule,
    CampNewsRoutingModule
  ],
  declarations: [CampNewsListComponent, CampNewsItemComponent]
})
export class CampNewsModule { }

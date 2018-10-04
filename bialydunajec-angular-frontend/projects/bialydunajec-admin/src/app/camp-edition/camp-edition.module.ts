import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CampEditionRoutingModule } from './camp-edition-routing.module';
import {CampEditionListComponent} from './component/camp-edition-list/camp-edition-list.component';

@NgModule({
  imports: [
    CommonModule,
    CampEditionRoutingModule
  ],
  declarations: [CampEditionListComponent]
})
export class CampEditionModule { }

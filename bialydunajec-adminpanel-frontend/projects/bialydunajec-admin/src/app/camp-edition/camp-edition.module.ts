import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CampEditionRoutingModule } from './camp-edition-routing.module';
import {CampEditionListComponent} from './component/camp-edition-list/camp-edition-list.component';
import {SharedModule} from '../shared/shared.module';

@NgModule({
  imports: [
    SharedModule,
    CampEditionRoutingModule
  ],
  declarations: [CampEditionListComponent]
})
export class CampEditionModule { }

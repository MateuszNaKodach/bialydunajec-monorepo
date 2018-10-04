import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CampEditionRoutingModule } from './camp-edition-routing.module';
import {CampEditionListComponent} from './component/camp-edition-list/camp-edition-list.component';
import {SharedModule} from '../shared/shared.module';
import { CampEditionEditComponent } from './component/camp-edition-edit/camp-edition-edit.component';

@NgModule({
  imports: [
    SharedModule,
    CampEditionRoutingModule
  ],
  declarations: [CampEditionListComponent, CampEditionEditComponent]
})
export class CampEditionModule { }

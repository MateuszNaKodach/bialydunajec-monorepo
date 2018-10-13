import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {SharedModule} from '../shared/shared.module';
import { AcademicMinistryEditComponent } from './component/academic-ministry-edit/academic-ministry-edit.component';
import {AcademicMinistryRoutingModule} from './academic-ministry.routing.module';

@NgModule({
  imports: [
    SharedModule,
    AcademicMinistryRoutingModule
  ],
  declarations: [AcademicMinistryEditComponent]
})
export class AcademicMinistryModule { }

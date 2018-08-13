import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AcademicMinistrySelectionComponent} from './academic-ministry-selection/academic-ministry-selection.component';
import {AcademicMinistryNamesListComponent} from './academic-ministry-names-list/academic-ministry-names-list.component';
import {FlexLayoutModule} from '@angular/flex-layout';
import {AcademicMinistriesComponent} from './academic-ministries/academic-ministries.component';
import {SharedModule} from '../shared/shared.module';
import { AcademicMinistryCardComponent } from './academic-ministry-card/academic-ministry-card.component';

@NgModule({
  imports: [
    SharedModule,
    FlexLayoutModule
  ],
  declarations: [AcademicMinistrySelectionComponent, AcademicMinistryNamesListComponent, AcademicMinistriesComponent, AcademicMinistryCardComponent],
  exports: [AcademicMinistriesComponent]
})
export class CottagesModule {
}

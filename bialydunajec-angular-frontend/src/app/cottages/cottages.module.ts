import {NgModule} from '@angular/core';
import {AcademicMinistrySelectionComponent} from './academic-ministry-selection/academic-ministry-selection.component';
import {FlexLayoutModule} from '@angular/flex-layout';
import {AcademicMinistriesComponent} from './academic-ministries/academic-ministries.component';
import {SharedModule} from '../shared/shared.module';
import { AcademicMinistryCardComponent } from './academic-ministry-card/academic-ministry-card.component';
import { CottageDetailsComponent } from './cottage-details/cottage-details.component';
import { CottagesNamesListComponent } from './cottage-details/cottages-names-list/cottages-names-list.component';

@NgModule({
  imports: [
    SharedModule,
    FlexLayoutModule
  ],
  declarations: [AcademicMinistrySelectionComponent, AcademicMinistriesComponent, AcademicMinistryCardComponent, CottageDetailsComponent, CottagesNamesListComponent],
  exports: [AcademicMinistriesComponent]
})
export class CottagesModule {
}

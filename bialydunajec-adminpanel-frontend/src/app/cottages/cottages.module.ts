import {NgModule} from '@angular/core';
import {AcademicMinistrySelectionComponent} from './academic-ministry-selection/academic-ministry-selection.component';
import {FlexLayoutModule} from '@angular/flex-layout';
import {AcademicMinistriesComponent} from './academic-ministries/academic-ministries.component';
import {SharedModule} from '../shared/shared.module';
import {AcademicMinistryCardComponent} from './academic-ministry-card/academic-ministry-card.component';
import {CottageDetailsComponent} from './cottage-details/cottage-details.component';
import {CottagesNamesListComponent} from './cottage-details/cottages-names-list/cottages-names-list.component';
import {CottagesRoutingModule} from './cottages-routing.module';

@NgModule({
  imports: [
    SharedModule,
    FlexLayoutModule,
    CottagesRoutingModule
  ],
  declarations: [
    AcademicMinistrySelectionComponent,
    AcademicMinistriesComponent,
    AcademicMinistryCardComponent,
    CottageDetailsComponent,
    CottagesNamesListComponent
  ],
  exports: [AcademicMinistriesComponent]
})
export class CottagesModule {
}

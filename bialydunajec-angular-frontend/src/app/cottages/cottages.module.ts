import {NgModule} from '@angular/core';
import {AcademicMinistrySelectionComponent} from './academic-ministry-selection/academic-ministry-selection.component';
import {FlexLayoutModule} from '@angular/flex-layout';
import {AcademicMinistriesComponent} from './academic-ministries/academic-ministries.component';
import {SharedModule} from '../shared/shared.module';
import {AcademicMinistryCardComponent} from './academic-ministry-card/academic-ministry-card.component';
import {CottageComponent} from './cottage/cottage.component';
import {CottagesNamesListComponent} from './cottage/cottages-names-list/cottages-names-list.component';
import {CottagesRoutingModule} from './cottages-routing.module';
import { CottageDetailsComponent } from './cottage/cottage-details/cottage-details.component';
import { CottageMinistryDetailsComponent } from './cottage/cottage-ministry-details/cottage-ministry-details.component';
import {PhotoInfoCardComponent} from './cottage/photo-info-card/photo-info-card.component';

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
    CottageComponent,
    CottagesNamesListComponent,
    CottageDetailsComponent,
    CottageMinistryDetailsComponent,
    PhotoInfoCardComponent
  ],
  exports: [AcademicMinistriesComponent]
})
export class CottagesModule {
}

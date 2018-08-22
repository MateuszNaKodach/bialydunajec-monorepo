import {NgModule} from '@angular/core';
import {AcademicMinistrySelectionComponent} from './component/academic-ministry-selection/academic-ministry-selection.component';
import {AcademicMinistriesComponent} from './component/academic-ministries/academic-ministries.component';
import {SharedModule} from '../shared/shared.module';
import {AcademicMinistryCardComponent} from './component/academic-ministry-card/academic-ministry-card.component';
import {CottageComponent} from './component/cottage/cottage.component';
import {CottagesNamesListComponent} from './component/cottages-names-list/cottages-names-list.component';
import {CottagesRoutingModule} from './cottages-routing.module';
import {CottageDetailsComponent} from './component/cottage-details/cottage-details.component';
import {CottageMinistryDetailsComponent} from './component/cottage-ministry-details/cottage-ministry-details.component';
import {AcademicMinistryService} from './service/academic-ministry.service';

@NgModule({
  imports: [
    SharedModule,
    CottagesRoutingModule
  ],
  declarations: [
    AcademicMinistrySelectionComponent,
    AcademicMinistriesComponent,
    AcademicMinistryCardComponent,
    CottageComponent,
    CottagesNamesListComponent,
    CottageDetailsComponent,
    CottageMinistryDetailsComponent
  ],
  exports: [AcademicMinistriesComponent],
  providers: [AcademicMinistryService]
})
export class CottagesModule {
}

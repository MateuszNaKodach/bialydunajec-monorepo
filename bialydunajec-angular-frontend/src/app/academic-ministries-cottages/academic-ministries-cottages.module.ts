import {NgModule} from '@angular/core';
import {AcademicMinistrySelectionComponent} from './component/academic-ministry-selection/academic-ministry-selection.component';
import {AcademicMinistriesComponent} from './component/academic-ministries/academic-ministries.component';
import {SharedModule} from '../shared/shared.module';
import {AcademicMinistryCardComponent} from './component/academic-ministry-card/academic-ministry-card.component';
import {AcademicMinistryInfoComponent} from './component/academic-ministry-info/academic-ministry-info.component';
import {AcademicMinistriesNamesComponent} from './component/academic-ministries-names/academic-ministries-names.component';
import {AcademicMinistriesCottagesRoutingModule} from './academic-ministries-cottages-routing.module';
import {AcademicMinistryCottageComponent} from './component/academic-ministry-cottage/academic-ministry-cottage.component';
import {AcademicMinistryDetailsComponent} from './component/academic-ministry-details/academic-ministry-details.component';
import {AcademicMinistryService} from './service/academic-ministry.service';

@NgModule({
  imports: [
    SharedModule,
    AcademicMinistriesCottagesRoutingModule
  ],
  declarations: [
    AcademicMinistrySelectionComponent,
    AcademicMinistriesComponent,
    AcademicMinistryCardComponent,
    AcademicMinistryInfoComponent,
    AcademicMinistriesNamesComponent,
    AcademicMinistryCottageComponent,
    AcademicMinistryDetailsComponent
  ],
  exports: [AcademicMinistriesComponent],
  providers: [AcademicMinistryService]
})
export class AcademicMinistriesCottagesModule {
}

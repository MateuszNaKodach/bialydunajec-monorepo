import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {coreRoutingPaths} from './core-routing.paths';
import {PanelLayoutComponent} from './component/panel-layout/panel-layout.component';
import {CampRegistrationsModule} from '../camp-registrations/camp-registrations.module';
import {CampEditionModule} from '../camp-edition/camp-edition.module';
import {AcademicMinistryModule} from '../academic-ministry/academic-ministry.module';

const coreRoutes: Routes = [
  {
    path: coreRoutingPaths.root, component: PanelLayoutComponent, children: [
      {path: coreRoutingPaths.campRegistrations, loadChildren: () => CampRegistrationsModule},
      {path: coreRoutingPaths.campEdition, loadChildren: () => CampEditionModule},
      {path: coreRoutingPaths.academicMinistry, loadChildren: () => AcademicMinistryModule}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(coreRoutes)],
  exports: [RouterModule]
})
export class CoreRoutingModule {
}

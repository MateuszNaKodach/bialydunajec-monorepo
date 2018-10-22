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
      {
        path: coreRoutingPaths.campRegistrations,
        loadChildren: '../camp-registrations/camp-registrations.module#CampRegistrationsModule',
      },
      {
        path: coreRoutingPaths.campEdition,
        loadChildren: '../camp-edition/camp-edition.module#CampEditionModule',
      },
      {
        path: coreRoutingPaths.academicMinistry,
        loadChildren: '../academic-ministry/academic-ministry.module#AcademicMinistryModule',
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(coreRoutes)],
  exports: [RouterModule]
})
export class CoreRoutingModule {
}

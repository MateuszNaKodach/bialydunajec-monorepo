import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {coreRoutingPaths} from './core-routing.paths';
import {PanelLayoutComponent} from './component/panel-layout/panel-layout.component';
import {CampRegistrationsModule} from '../camp-registrations/camp-registrations.module';

const coreRoutes: Routes = [
  {
    path: coreRoutingPaths.root, component: PanelLayoutComponent, children: [
      {path: coreRoutingPaths.campRegistrations, loadChildren: () => CampRegistrationsModule}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(coreRoutes)],
  exports: [RouterModule]
})
export class CoreRoutingModule {
}

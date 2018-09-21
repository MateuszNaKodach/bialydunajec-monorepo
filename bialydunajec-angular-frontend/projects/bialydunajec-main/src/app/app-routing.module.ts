import {NgModule} from '@angular/core';
import {Routes, RouterModule, PreloadAllModules} from '@angular/router';
import {appRoutingPaths} from './app-routing.paths';

const routes: Routes = [
  {
    path: appRoutingPaths.aboutCamp,
    loadChildren: './about-camp/about-camp.module#AboutCampModule'
  },
  {
    path: appRoutingPaths.academicMinistriesCottages,
    loadChildren: './academic-ministries-cottages/academic-ministries-cottages.module#AcademicMinistriesCottagesModule'
  },
  {
    path: appRoutingPaths.transport,
    loadChildren: './transport/transport.module#TransportModule'
  },
  {
    path: appRoutingPaths.contact,
    loadChildren: './contact/contact.module#ContactModule'
  },
  {
    path: appRoutingPaths.campersRegistration,
    loadChildren: './campers-registration/campers-registration.module#CampersRegistrationModule'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {preloadingStrategy: PreloadAllModules})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

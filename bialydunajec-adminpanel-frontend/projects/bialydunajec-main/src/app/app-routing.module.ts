import {NgModule} from '@angular/core';
import {Routes, RouterModule, PreloadAllModules} from '@angular/router';
import {appRoutingPaths} from './app-routing.paths';
import {NotFoundComponent} from './core/component/not-found/not-found.component';
import {MoreMenuOptionsComponent} from './core/component/more-menu-options/more-menu-options.component';

const routes: Routes = [
  {
    path: appRoutingPaths.root,
    redirectTo: appRoutingPaths.campNews,
    pathMatch: 'full'
  },
  {
    path: appRoutingPaths.campNews,
    loadChildren: './camp-news/camp-news.module#CampNewsModule'
  },
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
  },
  {
    path: appRoutingPaths.campGallery,
    loadChildren: './camp-gallery/camp-gallery.module#CampGalleryModule'
  },
  {
    path: appRoutingPaths.more,
    component: MoreMenuOptionsComponent
  },
  {
    path: appRoutingPaths.notFound,
    component: NotFoundComponent
  },
  {
    path: '**',
    redirectTo: appRoutingPaths.notFound
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {preloadingStrategy: PreloadAllModules})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

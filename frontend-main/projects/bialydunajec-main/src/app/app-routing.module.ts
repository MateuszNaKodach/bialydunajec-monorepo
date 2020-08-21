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
    loadChildren: () => import('./camp-news/camp-news.module').then(m => m.CampNewsModule)
  },
  {
    path: appRoutingPaths.aboutCamp,
    loadChildren: () => import('./about-camp/about-camp.module').then(m => m.AboutCampModule)
  },
  {
    path: appRoutingPaths.academicMinistriesCottages,
    loadChildren: () => import('./academic-ministries-cottages/academic-ministries-cottages.module').then(m => m.AcademicMinistriesCottagesModule)
  },
  {
    path: appRoutingPaths.transport,
    loadChildren: () => import('./transport/transport.module').then(m => m.TransportModule)
  },
  {
    path: appRoutingPaths.contact,
    loadChildren: () => import('./contact/contact.module').then(m => m.ContactModule)
  },
  {
    path: appRoutingPaths.campersRegistration,
    loadChildren: () => import('./campers-registration/campers-registration.module').then(m => m.CampersRegistrationModule)
  },
  {
    path: appRoutingPaths.campGallery,
    loadChildren: () => import('./camp-gallery/camp-gallery.module').then(m => m.CampGalleryModule)
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

import {NgModule} from '@angular/core';
import {Routes, RouterModule, PreloadAllModules} from '@angular/router';

const routes: Routes = [
  {path: 'o-obozie', loadChildren: './about-camp/about-camp.module#AboutCampModule'},
  {path: 'duszpasterstwa-i-chatki', loadChildren: './academic-ministries-cottages/academic-ministries-cottages.module#AcademicMinistriesCottagesModule'},
  {path: 'dojazd', loadChildren: './transport/transport.module#TransportModule'},
  {path: 'kontakt', loadChildren: './contact/contact.module#ContactModule'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {preloadingStrategy: PreloadAllModules})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

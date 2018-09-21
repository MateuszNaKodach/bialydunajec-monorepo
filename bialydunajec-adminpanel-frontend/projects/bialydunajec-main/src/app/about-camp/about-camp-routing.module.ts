import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {FaqComponent} from './component/faq/faq.component';
import {aboutCampPaths} from './about-camp-routing.paths';


const aboutCampRoutes: Routes = [
  {path: aboutCampPaths.root, redirectTo: aboutCampPaths.faq, pathMatch: 'full'},
  {path: aboutCampPaths.faq, component: FaqComponent}
];

@NgModule({
  imports: [
    RouterModule.forChild(aboutCampRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class AboutCampRoutingModule {
}

import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {FaqComponent} from './component/faq/faq.component';


const aboutCampRoutes: Routes = [
  {path: '', component: FaqComponent}
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

import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {FaqComponent} from './about-camp/faq/faq.component';
import {AcademicMinistriesComponent} from './cottages/academic-ministries/academic-ministries.component';

const routes: Routes = [
  {path: '', component: AcademicMinistriesComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

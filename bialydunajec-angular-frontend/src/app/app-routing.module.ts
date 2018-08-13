import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {FaqComponent} from './about-camp/faq/faq.component';
import {AcademicMinistriesComponent} from './cottages/academic-ministries/academic-ministries.component';

const routes: Routes = [
  {path: '', redirectTo: 'duszpasterstwa-i-chatki', pathMatch: 'full'},
  {path: 'o-obozie', component: FaqComponent},
  {path: 'duszpasterstwa-i-chatki', component: AcademicMinistriesComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {FaqComponent} from './about-camp/faq/faq.component';

const routes: Routes = [
  {path: '', component: FaqComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

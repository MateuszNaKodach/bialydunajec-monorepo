import {NgModule} from '@angular/core';
import {Routes, RouterModule, PreloadAllModules} from '@angular/router';
import {FaqComponent} from './about-camp/faq/faq.component';

const routes: Routes = [
  {path: 'o-obozie', component: FaqComponent},
  {path: 'duszpasterstwa-i-chatki', loadChildren: './cottages/cottages.module#CottagesModule'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {preloadingStrategy: PreloadAllModules})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

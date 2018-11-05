import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {aboutCampPaths} from '../about-camp/about-camp-routing.paths';
import {CampNewsListComponent} from './component/camp-news-list/camp-news-list.component';

const routes: Routes = [
  {path: aboutCampPaths.root, component: CampNewsListComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CampNewsRoutingModule { }

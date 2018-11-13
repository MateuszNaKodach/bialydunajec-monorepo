import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {campGalleryRoutingPaths} from './camp-gallery-routing.paths';
import {CampGalleryComponent} from './component/camp-gallery/camp-gallery.component';

const routes: Routes = [
  {path: campGalleryRoutingPaths.root, component: CampGalleryComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CampGalleryRoutingModule { }

import { NgModule } from '@angular/core';

import { CampGalleryRoutingModule } from './camp-gallery-routing.module';
import { CampGalleryComponent } from './component/camp-gallery/camp-gallery.component';
import {SharedModule} from '../shared/shared.module';

@NgModule({
  imports: [
    SharedModule,
    CampGalleryRoutingModule
  ],
  declarations: [CampGalleryComponent]
})
export class CampGalleryModule { }

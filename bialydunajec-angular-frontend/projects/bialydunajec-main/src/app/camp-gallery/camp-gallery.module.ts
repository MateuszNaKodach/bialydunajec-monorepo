import { NgModule } from '@angular/core';

import { CampGalleryRoutingModule } from './camp-gallery-routing.module';
import { CampGalleryComponent } from './component/camp-gallery/camp-gallery.component';
import {SharedModule} from '../shared/shared.module';
import {GalleryModule} from '@ngx-gallery/core';
import {GallerizeModule} from '@ngx-gallery/gallerize';
import {LightboxModule} from '@ngx-gallery/lightbox';

@NgModule({
  imports: [
    SharedModule,
    CampGalleryRoutingModule,
    GalleryModule.withConfig({dots: true, imageSize: 'cover', thumb: false}),
    GallerizeModule,
    LightboxModule
  ],
  declarations: [CampGalleryComponent]
})
export class CampGalleryModule { }

import {Component, OnInit} from '@angular/core';
import {GalleryItem, ImageItem} from '@ngx-gallery/core';

@Component({
  selector: 'bda-camp-gallery',
  templateUrl: './camp-gallery.component.html',
  styleUrls: ['./camp-gallery.component.scss']
})
export class CampGalleryComponent implements OnInit {

  images: GalleryItem[];

  constructor() {
  }

  ngOnInit() {
    this.images = Array.from(Array(99).keys())
      .slice(1)
      .map(it =>  new ImageItem({src: `/assets/images/gallery/camp-edition/35/uncategorized/${it}.jpg`}));
  }

}

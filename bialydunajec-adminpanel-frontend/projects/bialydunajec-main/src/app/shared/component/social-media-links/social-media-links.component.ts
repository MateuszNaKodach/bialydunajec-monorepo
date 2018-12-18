import {Component, Input, OnInit} from '@angular/core';
import {SocialMediaLinkViewModel} from './social-media-links.view-model';

@Component({
  selector: 'bda-social-media-links',
  templateUrl: './social-media-links.component.html',
  styleUrls: ['./social-media-links.component.scss']
})
export class SocialMediaLinksComponent implements OnInit {

  @Input() socialMedia: any; //SocialMediaLinkViewModel[]

  constructor() {
  }

  ngOnInit() {
  }

}

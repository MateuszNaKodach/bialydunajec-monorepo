import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'bda-photo-info-card',
  templateUrl: './photo-info-card.component.html',
  styleUrls: ['./photo-info-card.component.scss']
})
export class PhotoInfoCardComponent implements OnInit {

  @Input() imageSrc: string;
  @Input() imagePosition: 'left' | 'right' = 'left';

  constructor() {
  }

  ngOnInit() {
  }

}

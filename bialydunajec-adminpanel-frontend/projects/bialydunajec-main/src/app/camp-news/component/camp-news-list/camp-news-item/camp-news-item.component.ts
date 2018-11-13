import {Component, Input, OnInit} from '@angular/core';
import {CampNewsDto} from '../../../service/rest/dto/camp-news-page.dto';

@Component({
  selector: 'bda-camp-news-item',
  templateUrl: './camp-news-item.component.html',
  styleUrls: ['./camp-news-item.component.scss']
})
export class CampNewsItemComponent implements OnInit {

  @Input() campNews: CampNewsDto;
  mouseOvered = false;

  constructor() {
  }

  ngOnInit() {
  }

}

import {Component, OnInit} from '@angular/core';
import {CampNewsEndpoint} from '../../service/rest/camp-news.endpoint';
import {CampNewsPageDto} from '../../service/rest/dto/camp-news-page.dto';
import {Observable} from 'rxjs';

@Component({
  selector: 'bda-camp-news-list',
  templateUrl: './camp-news-list.component.html',
  styleUrls: ['./camp-news-list.component.scss']
})
export class CampNewsListComponent implements OnInit {

  campNews$: Observable<CampNewsPageDto>;

  constructor(private campNewsEndpoint: CampNewsEndpoint) {
  }

  ngOnInit() {
    this.campNews$ = this.campNewsEndpoint.getLastCampNews();
  }

}

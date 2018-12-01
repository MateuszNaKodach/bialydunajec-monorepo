import {Component, OnInit} from '@angular/core';
import {CampNewsEndpoint} from '../../service/rest/camp-news.endpoint';
import {CampNewsDto, CampNewsPageDto} from '../../service/rest/dto/camp-news-page.dto';
import {Observable} from 'rxjs';
import {map, take} from 'rxjs/operators';

@Component({
  selector: 'bda-camp-news-list',
  templateUrl: './camp-news-list.component.html',
  styleUrls: ['./camp-news-list.component.scss']
})
export class CampNewsListComponent implements OnInit {

  campNews$: Observable<CampNewsDto[]>;

  constructor(private campNewsEndpoint: CampNewsEndpoint) {
  }

  ngOnInit() {
    this.campNews$ = this.campNewsEndpoint.getLastCampNews()
      .pipe(
        map(it => it.content.slice(0, 5))
      );
  }

}

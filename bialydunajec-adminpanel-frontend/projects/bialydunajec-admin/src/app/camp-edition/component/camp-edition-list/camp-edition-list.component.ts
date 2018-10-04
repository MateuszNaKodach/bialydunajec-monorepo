import {Component, OnInit} from '@angular/core';
import {CampEditionEndpoint} from '../../service/rest/camp-edition.endpoint';
import {CampEditionService} from '../../service/camp-edition.service';
import {CampEditionResponse} from '../../service/rest/response/camp-edition.response';
import {Observable} from 'rxjs';

@Component({
  selector: 'bda-admin-camp-edition-list',
  templateUrl: './camp-edition-list.component.html',
  styleUrls: ['./camp-edition-list.component.less']
})
export class CampEditionListComponent implements OnInit {

  campEditions: Observable<CampEditionResponse[]>;

  constructor(private campEditionEndpoint: CampEditionEndpoint) {
  }

  ngOnInit() {
    this.campEditions = this.campEditionEndpoint.getAllCampEditions();
  }

  onClick() {
    // this.campEditionService.updateCampEditions();
  }
}

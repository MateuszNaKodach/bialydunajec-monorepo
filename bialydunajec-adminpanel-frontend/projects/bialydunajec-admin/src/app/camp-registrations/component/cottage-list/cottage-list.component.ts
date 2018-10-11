import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {CampEditionResponse} from '../../../camp-edition/service/rest/response/camp-edition.response';
import {CampRegistrationsEditionResponse} from '../../service/rest/response/camp-registrations-edition.response';
import {CampRegistrationsEndpoint} from '../../service/rest/camp-registrations.endpoint';
import {campRegistrationsRoutingPaths} from '../../camp-registrations-routing.paths';
import {coreRoutingPaths} from '../../../core/core-routing.paths';

@Component({
  selector: 'bda-admin-cottage-list',
  templateUrl: './cottage-list.component.html',
  styleUrls: ['./cottage-list.component.less']
})
export class CottageListComponent implements OnInit {

  coreRoutingPaths = coreRoutingPaths;
  campRegistrationsRoutingPaths = campRegistrationsRoutingPaths;
  availableCampEditions: Observable<CampEditionResponse[]>;
  selectedCampRegistrations: Observable<CampRegistrationsEditionResponse>;

  constructor(private campRegistrationsEndpoint: CampRegistrationsEndpoint) {
  }

  ngOnInit() {
    this.availableCampEditions = this.campRegistrationsEndpoint.getAllCampEditions();
  }

  onCampEditionIdSelected(selectedCampEditionId: number) {
    this.selectedCampRegistrations = this.campRegistrationsEndpoint.getCampRegistrationsEditionById(selectedCampEditionId);
  }

}

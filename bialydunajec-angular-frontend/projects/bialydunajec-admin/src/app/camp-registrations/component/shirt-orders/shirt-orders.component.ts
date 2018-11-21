import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {CampEditionResponse} from '../../../camp-edition/service/rest/response/camp-edition.response';
import {CampRegistrationsEndpoint} from '../../service/rest/camp-registrations.endpoint';

@Component({
  selector: 'bda-admin-shirt-orders',
  templateUrl: './shirt-orders.component.html',
  styleUrls: ['./shirt-orders.component.less']
})
export class ShirtOrdersComponent implements OnInit {

  availableCampEditions$: Observable<CampEditionResponse[]>;
  currentCampEdition: number;

  constructor(private campRegistrationsEndpoint: CampRegistrationsEndpoint) {
  }

  ngOnInit() {
    this.availableCampEditions$ = this.campRegistrationsEndpoint.getAllCampEditions();
  }

  onCampEditionIdSelected(selectedCampEditionId: number) {
    this.currentCampEdition = selectedCampEditionId;
  }

}

import {Component, Input, OnInit} from '@angular/core';
import {CottageResponse} from '../../../service/rest/response/cottage.response';
import {campRegistrationsRoutingPaths} from '../../../camp-registrations-routing.paths';
import {Observable} from 'rxjs';
import {CampRegistrationsEndpoint} from '../../../service/rest/camp-registrations.endpoint';
import {map} from 'rxjs/operators';

@Component({
  selector: 'bda-admin-cottage-card',
  templateUrl: './cottage-card.component.html',
  styleUrls: ['./cottage-card.component.less']
})
export class CottageCardComponent implements OnInit {

  campRegistrationsRoutingPaths = campRegistrationsRoutingPaths;
  @Input() cottage: CottageResponse;
  campParticipantsPercentByCottageId: Observable<{ cottageId: string, percent: number }>;

  constructor(private campRegistrationsEndpoint: CampRegistrationsEndpoint) {
  }

  ngOnInit() {
    this.campParticipantsPercentByCottageId = this.campRegistrationsEndpoint.countCampParticipantsByCottageId(this.cottage.cottageId)
      .pipe(
        map((response: { cottageId: string, campParticipantsCount: number }) => {
          return {
            cottageId: response.cottageId,
            percent: (!response.campParticipantsCount || Number.isNaN(response.campParticipantsCount)) ? 0 : Math.round(response.campParticipantsCount / this.cottage.cottageSpace.fullCapacity * 100)
          };
        })
      );
  }

}

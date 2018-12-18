import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
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
  @Output() deleteConfirm = new EventEmitter();
  campParticipantsPercentByCottageId: Observable<{ cottageId: string, percent: number }>;
  campParticipantCount: number;

  constructor(private campRegistrationsEndpoint: CampRegistrationsEndpoint) {
  }

  ngOnInit() {
    this.campParticipantsPercentByCottageId = this.campRegistrationsEndpoint.countCampParticipantsByCottageId(this.cottage.cottageId)
      .pipe(
        map((response: { cottageId: string, campParticipantsCount: number }) => {
          this.campParticipantCount = response.campParticipantsCount;
          return {
            cottageId: response.cottageId,
            percent: this.cottage.cottageSpace.fullCapacity === 0 ? null : (!response.campParticipantsCount || Number.isNaN(response.campParticipantsCount)) ? 0 : Math.round(response.campParticipantsCount / this.cottage.cottageSpace.fullCapacity * 100)
          };
        })
      );
  }

}

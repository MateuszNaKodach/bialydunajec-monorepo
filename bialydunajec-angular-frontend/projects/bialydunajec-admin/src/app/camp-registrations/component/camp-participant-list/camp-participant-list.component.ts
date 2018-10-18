import {Component, OnInit} from '@angular/core';
import {CampRegistrationsEndpoint} from '../../service/rest/camp-registrations.endpoint';
import {PageDto} from '../../service/rest/dto/page.dto';
import {CampRegistrationsResponse} from '../../service/rest/response/camp-registrations.response';
import {CampParticipantResponse} from '../../service/rest/response/camp-participant.response';
import {Observable} from 'rxjs';
import {CampEditionResponse} from '../../../camp-edition/service/rest/response/camp-edition.response';
import {tap} from 'rxjs/operators';

@Component({
  selector: 'bda-admin-camp-participant-list',
  templateUrl: './camp-participant-list.component.html',
  styleUrls: ['./camp-participant-list.component.less']
})
export class CampParticipantListComponent implements OnInit {

  availableCampEditions: Observable<CampEditionResponse[]>;
  campParticipants: CampParticipantResponse[] = [];

  constructor(private campRegistrationsEndpoint: CampRegistrationsEndpoint) {
  }

  ngOnInit() {
    this.availableCampEditions = this.campRegistrationsEndpoint.getAllCampEditions();

    this.campRegistrationsEndpoint.getPageOfAllCampParticipants(0, 100)
      .subscribe(
        (response: PageDto<CampParticipantResponse>) => {
          this.campParticipants = response.content;
          console.log('Camp participants', this.campParticipants);
        }
      );
  }

  onCampEditionIdSelected(selectedCampEditionId: number) {

  }

}

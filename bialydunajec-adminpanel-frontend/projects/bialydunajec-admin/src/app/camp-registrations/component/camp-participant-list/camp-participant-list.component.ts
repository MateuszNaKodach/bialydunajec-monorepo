import {Component, OnInit} from '@angular/core';
import {CampRegistrationsEndpoint} from '../../service/rest/camp-registrations.endpoint';
import {PageDto} from '../../service/rest/dto/page.dto';
import {CampRegistrationsResponse} from '../../service/rest/response/camp-registrations.response';
import {CampParticipantResponse} from '../../service/rest/response/camp-participant.response';
import {Observable} from 'rxjs';
import {CampEditionResponse} from '../../../camp-edition/service/rest/response/camp-edition.response';
import {finalize, tap} from 'rxjs/operators';

@Component({
  selector: 'bda-admin-camp-participant-list',
  templateUrl: './camp-participant-list.component.html',
  styleUrls: ['./camp-participant-list.component.less']
})
export class CampParticipantListComponent implements OnInit {

  availableCampEditions: Observable<CampEditionResponse[]>;
  campParticipants: CampParticipantResponse[] = [];

  currentCampEdition: number;
  // TableUI:
  tableIsLoading = false;
  pageIndex: number = 0;
  pageSize: number = 20;
  total: number;
  currentPageElements: number;

  constructor(private campRegistrationsEndpoint: CampRegistrationsEndpoint) {
  }

  ngOnInit() {
    this.availableCampEditions = this.campRegistrationsEndpoint.getAllCampEditions();
  }

  onCampEditionIdSelected(selectedCampEditionId: number) {
    this.currentCampEdition = selectedCampEditionId;
    this.updateCampRegistrationsTable(selectedCampEditionId);
  }

  private updateCampRegistrationsTable(selectedCampEditionId: number) {
    this.updateCampRegistrations(selectedCampEditionId, this.pageIndex - 1, this.pageSize);
  }

  private updateCampRegistrations(selectedCampEditionId: number, pageNumber: number, pageSize: number) {
    this.tableIsLoading = true;
    this.campRegistrationsEndpoint.getPageOfCampParticipantsByCampRegistrationsEditionId(selectedCampEditionId, pageNumber, pageSize)
      .pipe(
        finalize(() => {
          this.tableIsLoading = false;
        }),
      )
      .subscribe(
        (response: PageDto<CampParticipantResponse>) => {
          this.total = response.totalElements;
          this.campParticipants = response.content;
          this.currentPageElements = response.numberOfElements;
          console.log('Camp participants', this.campParticipants);
        }
      );
  }

  onPageIndexChange(pageNumber) {
    console.log('page index changed:', pageNumber);
    this.pageIndex = pageNumber;
    this.updateCampRegistrationsTable(this.currentCampEdition);
  }
}

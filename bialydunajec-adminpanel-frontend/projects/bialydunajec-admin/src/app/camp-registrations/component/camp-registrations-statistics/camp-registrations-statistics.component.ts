import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {CampEditionResponse} from '../../../camp-edition/service/rest/response/camp-edition.response';
import {CampRegistrationsEndpoint} from '../../service/rest/camp-registrations.endpoint';
import {InternalStorage} from '../../../shared/localstorage/internal-storage.service';

@Component({
  selector: 'bda-admin-camp-registrations-statistics',
  templateUrl: './camp-registrations-statistics.component.html',
  styleUrls: ['./camp-registrations-statistics.component.less']
})
export class CampRegistrationsStatisticsComponent implements OnInit {

  availableCampEditions$: Observable<CampEditionResponse[]>;
  currentCampEdition: number;

  campParticipantsByCottageStats: any[] = [];

  cottagesFillRatio: any[] = [];

  constructor(private campRegistrationsEndpoint: CampRegistrationsEndpoint, private internalStorage: InternalStorage) {
  }

  ngOnInit() {
    this.availableCampEditions$ = this.campRegistrationsEndpoint.getAllCampEditions();
  }

  onCampEditionIdSelected(selectedCampEditionId: number) {
    this.currentCampEdition = selectedCampEditionId;
    this.internalStorage.storeSelectedCampEditionId(this.currentCampEdition);
    this.loadCampRegistrationsEditionStats();
  }

  loadCampRegistrationsEditionStats() {
    this.campRegistrationsEndpoint.getCampRegistrationsStatisticsByCampRegistrationsEditionId(this.currentCampEdition)
      .subscribe(response => {
        const stats = response.cottagesStats;
        this.campParticipantsByCottageStats = stats
          .map(it => {
            return {name: it.cottageName, value: it.maleCampParticipantsAmount + it.femaleCampParticipantsAmount};
          });
        this.cottagesFillRatio = stats
          .map(it => {
            const value = it.cottageFillRatio == 'Infinity' ? 1 : it.cottageFillRatio;
            return {name: it.cottageName, value: value};
          });
      });
  }

}

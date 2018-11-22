import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {CampEditionResponse} from '../../../camp-edition/service/rest/response/camp-edition.response';
import {CampRegistrationsEndpoint} from '../../service/rest/camp-registrations.endpoint';

@Component({
  selector: 'bda-admin-camp-registrations-statistics',
  templateUrl: './camp-registrations-statistics.component.html',
  styleUrls: ['./camp-registrations-statistics.component.less']
})
export class CampRegistrationsStatisticsComponent implements OnInit {

  availableCampEditions$: Observable<CampEditionResponse[]>;
  currentCampEdition: number;

  single: any[] = [];
  view: any[] = [700, 400];

  // options
  showXAxis = true;
  showYAxis = true;
  gradient = false;
  showXAxisLabel = true;
  xAxisLabel = 'Chatka';
  showYAxisLabel = true;
  yAxisLabel = 'Liczba uczestników';


  constructor(private campRegistrationsEndpoint: CampRegistrationsEndpoint) {
  }

  ngOnInit() {
    this.availableCampEditions$ = this.campRegistrationsEndpoint.getAllCampEditions();
  }

  onCampEditionIdSelected(selectedCampEditionId: number) {
    this.currentCampEdition = selectedCampEditionId;
    this.loadCampRegistrationsEditionStats();
  }

  loadCampRegistrationsEditionStats() {
    this.campRegistrationsEndpoint.getCampRegistrationsStatisticsByCampRegistrationsEditionId(this.currentCampEdition)
      .subscribe(response => {
        const stats = response.cottagesStats;
        this.single = stats.map(s => {
          return {name: s.cottageName, value: s.maleCampParticipantsAmount + s.femaleCampParticipantsAmount};
        });
      });
  }

}

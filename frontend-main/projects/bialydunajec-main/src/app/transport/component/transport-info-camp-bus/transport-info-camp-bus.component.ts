import {Component, OnInit, Output} from '@angular/core';
import {CampBusLine} from '../../model/camp-bus-line.model';
import {CampTransportService} from '../../service/camp-transport.service';

@Component({
  selector: 'bda-transport-info-camp-bus',
  templateUrl: './transport-info-camp-bus.component.html',
  styleUrls: ['./transport-info-camp-bus.component.scss']
})
export class TransportInfoCampBusComponent implements OnInit {

  @Output() selectedCampBusLine: CampBusLine;
  private wroclawCampBusLine: CampBusLine;
  private opoleCampBusLine: CampBusLine;

  lat: number = 51.678418;
  lng: number = 7.809007;

  constructor(private campTransportService: CampTransportService) {
  }

  ngOnInit() {
    this.wroclawCampBusLine = this.campTransportService.getCampBusFromCity('Wrocław');
    this.opoleCampBusLine = this.campTransportService.getCampBusFromCity('Opole');
    this.selectedCampBusLine = this.wroclawCampBusLine;
  }

  selectBusFrom(originBusStopCity: string) {
    console.log('Selected bus: ', originBusStopCity);
    this.selectedCampBusLine =  originBusStopCity === 'Opole' ? this.opoleCampBusLine : this.wroclawCampBusLine;
  }

}

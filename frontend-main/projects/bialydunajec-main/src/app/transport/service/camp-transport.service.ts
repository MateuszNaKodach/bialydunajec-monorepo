import {Injectable} from '@angular/core';
import {BusStop, CampBusLine, CoordinatorContact} from '../model/camp-bus-line.model';
import {Address} from '../../shared/model/address.model';
import {Money} from '../../shared/model/money.model';
import {GeoLocation} from '../../shared/model/geo-location.model';

@Injectable({
  providedIn: 'root'
})
export class CampTransportService {

  constructor() {
  }

  getAllCampBusLines() {
    return [
      new CampBusLine(
        'asdasd-wroclaw-bus123',
        new BusStop(
          new Date(2018, 9, 4, 8, 0),
          new Address('ul. Marka Pietrusiewicza', '84', '50-027', 'Wrocław', 'Obok Aquaparku i wzgórza Andersa'),
          new GeoLocation(51.091841, 17.0300049)
        ),
        new BusStop(
          new Date(2018, 9, 4, 13, 0)
        ),
        'Autokar TOMEX',
        'Pod wyznaczonym adresem będie na Ciebie czekał autokar z tablicą "Biały Dunajec 2018"',
        new Money(57, 'PLN'),
        new CoordinatorContact('Marcin', 'Goleń', '123 123 123', null, 'tylko sytuacje awaryjne!'),
        true
      ),
      new CampBusLine(
        'asdasd-opole-bus2018',
        new BusStop(
          new Date(2018, 9, 4, 9, 0),
          new Address('ul. Opolska', '84', '45-987', 'Opole'),
          new GeoLocation(50.6684837, 17.9203079)
        ),
        new BusStop(
          new Date(2018, 9, 4, 13, 0)
        ),
        'Autokar TOMEX',
        'Pod wyznaczonym adresem będie na Ciebie czekał autokar z tablicą "Biały Dunajec 2018"',
        new Money(57, 'PLN'),
        new CoordinatorContact('Marcin', 'Goleń', '123 123 123', null, 'tylko sytuacje awaryjne!'),
        false
      )
    ];
  }

  getCampBusFromCity(originCity: string) {
    return this.getAllCampBusLines()
      .find(campBusLine => campBusLine.origin.address.city === originCity);
  }


}

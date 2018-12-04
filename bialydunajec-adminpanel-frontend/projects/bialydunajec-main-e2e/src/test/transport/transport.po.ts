import {browser, by, element} from 'protractor';

export class TransportPage {

  navigateToTransportPage() {
    return browser.get('/dojazd');
  }

  getCampBusDepartureMarker() {
    return element(by.className('bda-camp-bus-departure-marker'));
  }

}



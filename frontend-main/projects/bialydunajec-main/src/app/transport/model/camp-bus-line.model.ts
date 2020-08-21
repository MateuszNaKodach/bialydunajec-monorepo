import {Money} from '../../shared/model/money.model';
import {Address} from '../../shared/model/address.model';
import {GeoLocation} from '../../shared/model/geo-location.model';

export class CampBusLine {
  campBusLineId: string;
  origin: BusStop;
  destination: BusStop;
  busName: string;
  description: string;
  oneWayCost: Money;
  coordinatorContact: CoordinatorContact;
  hasFreeSeat: boolean;
  additionalNotes: string;


  constructor(campBusLineId: string,
              origin: BusStop,
              destination: BusStop,
              busName: string,
              description: string,
              oneWayCost: Money,
              coordinatorContact: CoordinatorContact,
              hasFreeSeat: boolean,
              additionalNotes: string = '') {
    this.campBusLineId = campBusLineId;
    this.origin = origin;
    this.destination = destination;
    this.busName = busName;
    this.description = description;
    this.additionalNotes = additionalNotes;
    this.oneWayCost = oneWayCost;
    this.coordinatorContact = coordinatorContact;
    this.hasFreeSeat = hasFreeSeat;
  }

  getOriginCity() {
    return this.origin.address.city;
  }
}

export class CoordinatorContact {
  firstName: string;
  lastName: string;
  phoneNumber: string;
  emailAddress: string;
  notes: string;


  constructor(firstName: string, lastName: string, phoneNumber: string, emailAddress: string = '', notes: string = '') {
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailAddress = emailAddress;
    this.phoneNumber = phoneNumber;
    this.notes = notes;
  }
}

export class BusStop {
  when: Date;
  address: Address;
  geoLocation: GeoLocation;

  constructor(when: Date, address: Address = null, geoLocation: GeoLocation = null) {
    this.when = when;
    this.address = address;
    this.geoLocation = geoLocation;
  }
}


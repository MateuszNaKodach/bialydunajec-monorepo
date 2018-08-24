import {Address} from '../../shared/model/address.model';
import {GeoLocation} from '../../shared/model/geo-location.model';
import {CottageBoss} from './cottage-boss.model';

export class CottageDetails {
  cottageId: string;
  academicMinistryId: string;
  resortName: string;
  pictureUrl: string;
  address: Address;
  conditions: CottageStayConditions;
  geoLocation: GeoLocation;
  cottageBoss: CottageBoss;

  constructor(cottageId: string,
              academicMinistryId: string,
              resortName: string,
              pictureUrl: string,
              address: Address,
              cottageBoss: CottageBoss = null,
              conditions: CottageStayConditions = null,
              geoLocation: GeoLocation = null) {
    this.cottageId = cottageId;
    this.academicMinistryId = academicMinistryId;
    this.resortName = resortName;
    this.pictureUrl = pictureUrl;
    this.address = address;
    this.cottageBoss = cottageBoss;
    this.conditions = conditions;
    this.geoLocation = geoLocation;
  }
}

export class CottageStayConditions {
  eating: StayCondition;
  sleeping: StayCondition;
  registering: StayCondition;


  constructor(eating: StayCondition, sleeping: StayCondition, registering: StayCondition = null) {
    this.eating = eating;
    this.sleeping = sleeping;
    this.registering = registering;
  }
}

export class StayCondition {
  title: string;
  description: string;

  constructor(title: string, description: string) {
    this.title = title;
    this.description = description;
  }
}

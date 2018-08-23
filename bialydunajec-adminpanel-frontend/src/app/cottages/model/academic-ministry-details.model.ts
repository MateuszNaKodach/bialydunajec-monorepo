import {Address} from '../../shared/model/address.model';
import {Facebook} from '../../shared/model/facebook.model';
import {AcademicMinistry} from './academic-ministry.model';
import {Priest} from './priest.model';
import {ExtendedDescription} from '../../shared/model/extended-description.model';

export class AcademicMinistryDetails {
  id: string;
  officialName: string;
  shortName: string;
  logoUrl: string;
  address: Address;
  website: string;
  facebook: Facebook;
  emailAddress: string;
  priests: Priest[];
  description: ExtendedDescription;


  constructor(id: string,
              officialName: string,
              shortName: string,
              logoUrl: string,
              address: Address,
              website: string,
              facebook: Facebook,
              emailAddress: string,
              priests: Priest[] = [],
              description: ExtendedDescription = null) {
    this.id = id;
    this.officialName = officialName;
    this.shortName = shortName;
    this.logoUrl = logoUrl;
    this.address = address;
    this.website = website;
    this.facebook = facebook;
    this.emailAddress = emailAddress;
    this.priests = priests;
    this.description = description;
  }

  toAcademicMinistry(): AcademicMinistry {
    return new AcademicMinistry(this.id, this.officialName, this.shortName, this.logoUrl);
  }
}

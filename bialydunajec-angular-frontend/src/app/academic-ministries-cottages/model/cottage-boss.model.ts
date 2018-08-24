import {ExtendedDescription} from '../../shared/model/extended-description.model';

export class CottageBoss {
  cottageBossId: string;
  firstName: string;
  lastName: string;
  phoneNumber: string;
  emailAddress: string;
  university: string;
  fieldOfStudy: string;
  photoUrl: string;
  personalDescription: ExtendedDescription;

  constructor(cottageBossId: string, firstName: string, lastName: string, phoneNumber: string, emailAddress: string, university: string, fieldOfStudy: string, photoUrl: string, personalDescription: ExtendedDescription) {
    this.cottageBossId = cottageBossId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    this.emailAddress = emailAddress;
    this.university = university;
    this.fieldOfStudy = fieldOfStudy;
    this.photoUrl = photoUrl;
    this.personalDescription = personalDescription;
  }
}

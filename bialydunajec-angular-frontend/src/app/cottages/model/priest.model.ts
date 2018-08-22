import {PersonalTitle} from '../../shared/model/personal-title.model';
import {ExtendedDescription} from '../../shared/model/extended-description.model';

export class Priest {
  id: string;
  firstName: string;
  lastName: string;
  personalTitle: PersonalTitle;
  pictureUrl: string;
  phoneNumber: string;
  emailAddress: string;
  personalDescription: ExtendedDescription;

  constructor(id: string,
              firstName: string,
              lastName: string,
              personalTitle: PersonalTitle = null,
              pictureUrl: string,
              phoneNumber: string = '',
              emailAddress: string,
              personalDescription: ExtendedDescription = ExtendedDescription.empty()) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.personalTitle = personalTitle;
    this.pictureUrl = pictureUrl;
    this.phoneNumber = phoneNumber;
    this.emailAddress = emailAddress;
    this.personalDescription = personalDescription;
  }
}

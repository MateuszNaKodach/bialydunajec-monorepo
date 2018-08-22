import {ExtendedDescription} from '../../shared/model/extended-description.model';

export class CadreMember {
  id: string;
  firstName: string;
  lastName: string;
  responsibility: string;
  academicMinistryName: string;
  photoUrl: string;
  contactInfo: ContactInfo;
  description: ExtendedDescription;

  constructor(id: string,
              firstName: string,
              lastName: string,
              responsibility: string,
              academicMinistryName: string,
              photoUrl: string,
              contactInfo: ContactInfo = null,
              description: ExtendedDescription = ExtendedDescription.empty()) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.responsibility = responsibility;
    this.academicMinistryName = academicMinistryName;
    this.photoUrl = photoUrl;
    this.contactInfo = contactInfo;
    this.description = description;
  }
}

export class ContactInfo {
  emailAddress: string;
  phoneNumber: string;
  facebookUrl: string;


  constructor(email: string = '', phoneNumber: string = '', facebookUrl: string = '') {
    this.emailAddress = email;
    this.phoneNumber = phoneNumber;
    this.facebookUrl = facebookUrl;
  }
}


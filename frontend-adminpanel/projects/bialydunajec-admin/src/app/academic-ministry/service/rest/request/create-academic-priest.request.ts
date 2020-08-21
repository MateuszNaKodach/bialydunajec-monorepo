import {ExtendedDescriptionDto} from '../../../../shared/service/rest/dto/extended-description.dto';
import {PersonalTitleDto} from '../dto/personal-title.dto';

export class CreateAcademicPriestRequest {
  firstName: string;
  lastName: string;
  personalTitle?: PersonalTitleDto;
  emailAddress?: string;
  phoneNumber?: string;
  description?: ExtendedDescriptionDto;
  photoUrl?: string;

  constructor(firstName: string, lastName: string, personalTitle: PersonalTitleDto, emailAddress: string, phoneNumber: string, description: ExtendedDescriptionDto, photoUrl: string) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.personalTitle = personalTitle;
    this.emailAddress = emailAddress;
    this.phoneNumber = phoneNumber;
    this.description = description;
    this.photoUrl = photoUrl;
  }
}

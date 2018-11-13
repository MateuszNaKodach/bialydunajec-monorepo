import {AddressDto} from '../../../../../../../bialydunajec-admin/src/app/shared/service/rest/dto/address.dto';
import {Gender} from '../../../../shared/model/gender.enum';

export class CampParticipantRegistrationRequest {
  cottageId: string;
  personalData: CamperPersonalDataDto;
  homeAddress: AddressDto;
  phoneNumber: String;
  emailAddress: String;
  camperEducation: CamperEducationDto;
  shirtOrder: CamperShirtOrderDto;


  constructor(
    cottageId: string,
    personalData: CamperPersonalDataDto,
    homeAddress: AddressDto,
    phoneNumber: String,
    emailAddress: String,
    camperEducation: CamperEducationDto,
    shirtOrder: CamperShirtOrderDto) {
    this.cottageId = cottageId;
    this.personalData = personalData;
    this.homeAddress = homeAddress;
    this.phoneNumber = phoneNumber;
    this.emailAddress = emailAddress;
    this.camperEducation = camperEducation;
    this.shirtOrder = shirtOrder;
  }
}

export class CamperPersonalDataDto {
  firstName: string;
  lastName: string;
  gender: Gender;
  pesel?: String;
  birthDate: Date;


  constructor(firstName: string, lastName: string, gender: Gender, pesel: String, birthDate: Date = null) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.pesel = pesel;
    this.birthDate = birthDate;
  }
}

export class CamperEducationDto {
  university: string;
  faculty: string;
  fieldOfStudy: string;
  highSchool?: string;
  isHighSchoolRecentGraduate: boolean;


  constructor(university: string, faculty: string, fieldOfStudy: string, highSchool: string, isHighSchoolRecentGraduate: boolean) {
    this.university = university;
    this.faculty = faculty;
    this.fieldOfStudy = fieldOfStudy;
    this.highSchool = highSchool;
    this.isHighSchoolRecentGraduate = isHighSchoolRecentGraduate;
  }
}

export class CamperShirtOrderDto {
  shirtColorOptionId: string;
  shirtSizeOptionId: string;

  constructor(shirtColorOptionId: string, shirtSizeOptionId: string) {
    this.shirtColorOptionId = shirtColorOptionId;
    this.shirtSizeOptionId = shirtSizeOptionId;
  }
}

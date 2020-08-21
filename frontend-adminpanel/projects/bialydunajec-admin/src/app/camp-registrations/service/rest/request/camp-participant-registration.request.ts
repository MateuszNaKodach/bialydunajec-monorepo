import {AddressDto} from '../../../../shared/service/rest/dto/address.dto';
import {Gender} from '../../../../shared/model/gender.enum';

export class CampParticipantRegistrationRequest {
  cottageId: string;
  personalData: CamperPersonalDataDto;
  homeAddress: AddressDto;
  phoneNumber: string;
  emailAddress: string;
  camperEducation: CamperEducationDto;
  shirtOrder: CamperShirtOrderDto;
  statisticalAnswers: StatisticalAnswersDto;
  meanOfTransport: string;


  constructor(cottageId: string, personalData: CamperPersonalDataDto, homeAddress: AddressDto, phoneNumber: string, emailAddress: string, camperEducation: CamperEducationDto, shirtOrder: CamperShirtOrderDto, statisticalAnswers: StatisticalAnswersDto, meanOfTransport: string) {
    this.cottageId = cottageId;
    this.personalData = personalData;
    this.homeAddress = homeAddress;
    this.phoneNumber = phoneNumber;
    this.emailAddress = emailAddress;
    this.camperEducation = camperEducation;
    this.shirtOrder = shirtOrder;
    this.statisticalAnswers = statisticalAnswers;
    this.meanOfTransport = meanOfTransport;
  }
}

export class CamperPersonalDataDto {
  firstName: string;
  lastName: string;
  gender: Gender;
  pesel?: string;
  birthDate: Date;


  constructor(firstName: string, lastName: string, gender: Gender, pesel: string, birthDate: Date = null) {
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

export class StatisticalAnswersDto {
  knowAboutCampFrom: string;
  onCampForTime: number;

  constructor(knowAboutCampFrom: string, onCampForTime: number) {
    this.knowAboutCampFrom = knowAboutCampFrom;
    this.onCampForTime = onCampForTime;
  }
}


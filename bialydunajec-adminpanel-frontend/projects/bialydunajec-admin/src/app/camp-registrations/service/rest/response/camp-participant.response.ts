import {AddressDto} from '../../../../shared/service/rest/dto/address.dto';
import {
  CamperEducationDto,
  CamperPersonalDataDto
} from '../../../../../../../bialydunajec-main/src/app/campers-registration/service/rest/request/camp-participant-registration.request';
import {Time} from '@angular/common';

export class CampParticipantResponse {
  campParticipantId: string;
  campRegistrationsEditionId: string;
  campParticipantRegistrationId: string;
  confirmedApplication?: CamperApplicationWithCottageDto;
  currentCamperData: CamperApplicationWithCottageDto;
  stayDuration: StayDurationDto;
  participationStatus: ParticipationStatusDto;
  registrationDate: Date;
  confirmationDate: Date;
  downPaymentPaidDate: Date;
  campBusSeatPaidDate: Date;
  campParticipationPaidDate: Date;
}

export class CamperApplicationWithCottageDto {
  cottage: {
    cottageId: string;
    cottageName: string;
  };
  personalData: CamperPersonalDataDto;
  homeAddress: AddressDto;
  phoneNumber: String;
  emailAddress: String;
  camperEducation: CamperEducationDto;
}

export enum ParticipationStatusDto {
  WAITING_FOR_CONFIRM = 'WAITING_FOR_CONFIRM',
  CONFIRMED_BY_CAMPER = 'CONFIRMED_BY_CAMPER',
  CONFIRMED_BY_AUTHORIZED = 'CONFIRMED_BY_AUTHORIZED'
}

export class CamperApplicationDto {
  cottageId: string;
  personalData: CamperPersonalDataDto;
  homeAddress: AddressDto;
  phoneNumber: String;
  emailAddress: String;
  camperEducation: CamperEducationDto;
}

export class StayDurationDto {
  checkInDate?: Date;
  checkInTime?: Time;
  checkOutDate?: Date;
  checkOutTime?: Time;
}

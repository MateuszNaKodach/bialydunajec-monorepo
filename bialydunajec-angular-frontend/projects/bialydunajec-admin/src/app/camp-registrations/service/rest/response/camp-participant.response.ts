import {AddressDto} from '../../../../shared/service/rest/dto/address.dto';
import {
  CamperEducationDto,
  CamperPersonalDataDto
} from '../../../../../../../bialydunajec-main/src/app/campers-registration/service/rest/request/camp-participant-registration.request';
import {Time} from '@angular/common';

export class CampParticipantResponse {
  campParticipantId: string;
  campRegistrationsEditionId: string;
  confirmedApplication?: CamperApplicationDto;
  currentCamperData: CamperApplicationDto;
  stayDuration: StayDurationDto;
  participationStatus: string;
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

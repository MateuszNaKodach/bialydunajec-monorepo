export class ShirtOrderReadModel {
  shirtOrderId: string;
  campRegistrationsEditionId: string;
  campParticipant: CampParticipant;
  campEditionShirtId: string;
  selectedOptions: SelectedOptions;
  placedDate: Date;
}

export class CampParticipant {
  campParticipantId: string;
  cottage: Cottage;
  pesel: string;
  firstName: string;
  lastName: string;
  emailAddress: string;
  phoneNumber: string;
  participationStatus: ParticipationStatusDto;
}

export enum ParticipationStatusDto {
  WAITING_FOR_CONFIRM = 'WAITING_FOR_CONFIRM',
  CONFIRMED_BY_CAMPER = 'CONFIRMED_BY_CAMPER',
  CONFIRMED_BY_AUTHORIZED = 'CONFIRMED_BY_AUTHORIZED'
}

export class Cottage {
  cottageId: string;
  name: string;
}

export class SelectedOptions {
  shirtColorOptionId: string;
  colorName: string;
  shirtSizeOptionId: string;
  sizeName: string;
  shirtType: ShirtTypeDto;
}

export enum ShirtTypeDto {
  FEMALE = 'FEMALE',
  MALE = 'MALE',
  UNISEX = 'UNISEX'
}

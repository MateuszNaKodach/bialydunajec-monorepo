export class PaymentCommitmentReadModel {
  paymentCommitmentId: string;
  type: PaymentCommitmentType;
  campRegistrationsEditionId: string;
  campParticipant: CampParticipantReadModel;
  campParticipantCottageAccountId: string;
  cottage: CottageReadModel;
  amount: number;
  description: string;
  deadlineDate: Date;
  isPaid: boolean;
  paidDate: Date;
}


export class CampParticipantReadModel {
  campParticipantId: string;
  pesel: string;
  firstName: string;
  lastName: string;
  emailAddress: string;
  phoneNumber: string;
}

export class CottageReadModel {
  cottageId: string;
  name: string;
}

export enum PaymentCommitmentType {
  CAMP_DOWN_PAYMENT = 'CAMP_DOWN_PAYMENT',
  CAMP_PARTICIPATION = 'CAMP_PARTICIPATION',
  CAMP_BUS_SEAT = 'CAMP_BUS_SEAT'


}

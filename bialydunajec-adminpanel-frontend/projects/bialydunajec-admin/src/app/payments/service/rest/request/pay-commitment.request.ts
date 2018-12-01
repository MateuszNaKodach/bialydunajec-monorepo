export class PayCommitmentRequest {
  campParticipantCottageAccountId: string;
  useAccountFunds: boolean;


  constructor(campParticipantCottageAccountId: string, useAccountFunds: boolean) {
    this.campParticipantCottageAccountId = campParticipantCottageAccountId;
    this.useAccountFunds = useAccountFunds;
  }
}

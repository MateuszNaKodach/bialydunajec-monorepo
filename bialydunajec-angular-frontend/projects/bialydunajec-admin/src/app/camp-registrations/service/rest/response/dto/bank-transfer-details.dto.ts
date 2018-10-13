export class BankTransferDetailsDto {
  accountNumber?: string;
  accountOwner?: string;
  accountOwnerAddress?: string;
  transferTitleTemplate: string;


  constructor(accountNumber: string = null, accountOwner: string = null, accountOwnerAddress: string = null, transferTitleTemplate: string = null) {
    this.accountNumber = accountNumber;
    this.accountOwner = accountOwner;
    this.accountOwnerAddress = accountOwnerAddress;
    this.transferTitleTemplate = transferTitleTemplate;
  }
}

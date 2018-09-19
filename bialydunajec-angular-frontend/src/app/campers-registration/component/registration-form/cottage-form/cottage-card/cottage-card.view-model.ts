export class CottageCardViewModel {
  cottageId: string;
  cottageName: string;
  cottageLogoUrl: string;

  constructor(cottageId: string, cottageName: string, cottageLogoUrl: string) {
    this.cottageId = cottageId;
    this.cottageName = cottageName;
    this.cottageLogoUrl = cottageLogoUrl;
  }
}

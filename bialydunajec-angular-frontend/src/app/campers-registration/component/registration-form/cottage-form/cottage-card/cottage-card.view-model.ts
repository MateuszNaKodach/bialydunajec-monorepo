export class CottageCardViewModel {
  cottageId: string;
  cottageName: string;
  cottageLogoUrl: string;
  hasSpace: boolean = true;

  constructor(cottageId: string, cottageName: string, cottageLogoUrl: string, hasSpace: boolean = true) {
    this.cottageId = cottageId;
    this.cottageName = cottageName;
    this.cottageLogoUrl = cottageLogoUrl;
    this.hasSpace = hasSpace;
  }
}

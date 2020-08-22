export class AcademicMinistry {
  id: string;
  officialName: string;
  shortName: string;
  logoUrl: string;

  constructor(id: string, officialName: string, shortName: string, logoUrl: string) {
    this.id = id;
    this.officialName = officialName;
    this.shortName = shortName;
    this.logoUrl = logoUrl;
  }
}

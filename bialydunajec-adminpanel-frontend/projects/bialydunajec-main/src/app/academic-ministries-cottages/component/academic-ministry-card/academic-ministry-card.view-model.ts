export class AcademicMinistryCardViewModel {
  id: string;
  name: string;
  logoUrl: string;

  constructor(id: string, name: string, logoUrl: string) {
    this.id = id;
    this.name = name;
    this.logoUrl = logoUrl;
  }
}

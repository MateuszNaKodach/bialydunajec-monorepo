export class AcademicMinistryNameResponse {
  academicMinistryId: string;
  officialName: string;
  shortName?: string;

  get displayName(): string {
    return this.shortName ? this.shortName : this.officialName;
  }
}

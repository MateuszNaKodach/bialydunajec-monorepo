import {ExtendedDescriptionDto, PlaceDto, SocialMediaDto} from '../request/create-academic-ministry.request';

export class AcademicMinistryResponse {
  academicMinistryId: string;
  officialName: string;
  shortName?: string;
  logoImageUrl?: string;
  place?: PlaceDto;
  socialMedia?: SocialMediaDto;
  emailAddress?: string;
  photoUrl?: string;
  description?: ExtendedDescriptionDto;

  get displayName(): string {
    return this.shortName ? this.shortName : this.officialName;
  }
}

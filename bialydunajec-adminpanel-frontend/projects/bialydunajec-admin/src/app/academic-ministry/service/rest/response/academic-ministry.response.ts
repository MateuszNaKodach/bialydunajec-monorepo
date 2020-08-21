import {PlaceDto} from '../../../../shared/service/rest/dto/place.dto';
import {SocialMediaDto} from '../../../../shared/service/rest/dto/social-media.dto';
import {ExtendedDescriptionDto} from '../../../../shared/service/rest/dto/extended-description.dto';

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

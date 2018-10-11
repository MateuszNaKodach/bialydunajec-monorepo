import {PlaceDto} from '../../../../shared/service/rest/dto/place.dto';
import {ExtendedDescriptionDto} from '../../../../shared/service/rest/dto/extended-description.dto';
import {SocialMediaDto} from '../../../../shared/service/rest/dto/social-media.dto';

export class CreateAcademicMinistryRequest {
  officialName: string;
  shortName?: string;
  logoImageUrl?: string;
  place?: PlaceDto;
  socialMedia?: SocialMediaDto;
  emailAddress?: string;
  photoUrl?: string;
  description?: ExtendedDescriptionDto;

  constructor(officialName: string, shortName: string, logoImageUrl: string, place: PlaceDto, socialMedia: SocialMediaDto, emailAddress: string, photoUrl: string, description: ExtendedDescriptionDto) {
    this.officialName = officialName;
    this.shortName = shortName;
    this.logoImageUrl = logoImageUrl;
    this.place = place;
    this.socialMedia = socialMedia;
    this.emailAddress = emailAddress;
    this.photoUrl = photoUrl;
    this.description = description;
  }
}

export class UpdateAcademicMinistryRequest {
  officialName: string;
  shortName?: string;
  logoImageUrl?: string;
  place?: PlaceDto;
  socialMedia?: SocialMediaDto;
  emailAddress?: string;
  photoUrl?: string;
  description?: ExtendedDescriptionDto;

  constructor(officialName: string, shortName: string, logoImageUrl: string, place: PlaceDto, socialMedia: SocialMediaDto, emailAddress: string, photoUrl: string, description: ExtendedDescriptionDto) {
    this.officialName = officialName;
    this.shortName = shortName;
    this.logoImageUrl = logoImageUrl;
    this.place = place;
    this.socialMedia = socialMedia;
    this.emailAddress = emailAddress;
    this.photoUrl = photoUrl;
    this.description = description;
  }
}


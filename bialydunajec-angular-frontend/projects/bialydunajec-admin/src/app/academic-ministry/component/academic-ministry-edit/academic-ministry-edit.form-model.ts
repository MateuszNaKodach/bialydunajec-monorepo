import {AcademicMinistryResponse} from '../../service/rest/response/academic-ministry.response';

export class AcademicMinistryEditFormModel {
  officialName: string;
  shortName?: string;
  logoImageUrl?: string;
  place?: {
    address: {
      street: string;
      homeNumber?: string;
      city: string;
      postalCode?: string;
    }
    geoLocation?: {
      latitude?: number;
      longitude?: number;
    }
  };
  socialMedia?: {
    webPageUrl?: string;
    facebookPageUrl?: string;
    facebookGroupUrl?: string;
    instagramUrl?: string;
    youTubeChannelUrl?: string;
  };
  emailAddress?: string;
  photoUrl?: string;
  description?: {
    title?: string;
    content: string;
  };


  constructor(officialName: string, shortName: string, logoImageUrl: string, place: { address: { street: string; homeNumber?: string; city: string; postalCode?: string }; geoLocation?: { latitude?: number; longitude?: number } }, socialMedia: { webPageUrl?: string; facebookPageUrl?: string; facebookGroupUrl?: string; instagramUrl?: string; youTubeChannelUrl?: string }, emailAddress: string, photoUrl: string, description: { title?: string; content: string }) {
    this.officialName = officialName;
    this.shortName = shortName;
    this.logoImageUrl = logoImageUrl;
    this.place = place;
    this.socialMedia = socialMedia;
    this.emailAddress = emailAddress;
    this.photoUrl = photoUrl;
    this.description = description;
  }

  static fromDto(dto: AcademicMinistryResponse) {
    return new AcademicMinistryEditFormModel(
      dto.officialName,
      dto.shortName,
      dto.logoImageUrl,
      {
        address: {
          street: dto.place && dto.place.address ? dto.place.address.street : null,
          homeNumber: dto.place && dto.place.address ? dto.place.address.homeNumber : null,
          city: dto.place && dto.place.address ? dto.place.address.city : null,
          postalCode: dto.place && dto.place.address ? dto.place.address.postalCode : null
        },
        geoLocation: {
          latitude: dto.place && dto.place.geoLocation ? dto.place.geoLocation.latitude : null,
          longitude: dto.place && dto.place.geoLocation ? dto.place.geoLocation.longitude : null
        }
      },
      {
        webPageUrl: dto.socialMedia ? dto.socialMedia.webPageUrl : null,
        facebookPageUrl: dto.socialMedia ? dto.socialMedia.facebookPageUrl : null,
        facebookGroupUrl: dto.socialMedia ? dto.socialMedia.facebookGroupUrl : null,
        instagramUrl: dto.socialMedia ? dto.socialMedia.instagramUrl : null,
        youTubeChannelUrl: dto.socialMedia ? dto.socialMedia.youTubeChannelUrl : null
      },
      dto.emailAddress,
      dto.photoUrl,
      {
        title: dto.description ? dto.description.title : null,
        content: dto.description ? dto.description.content : null
      }
    );
  }
}

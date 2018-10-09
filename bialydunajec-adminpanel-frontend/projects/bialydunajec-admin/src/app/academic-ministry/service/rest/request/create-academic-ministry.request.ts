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

export class PlaceDto {
  address: AddressDto;
  geoLocation?: GeoLocationDto;


  constructor(address: AddressDto, geoLocation: GeoLocationDto) {
    this.address = address;
    this.geoLocation = geoLocation;
  }
}

export class AddressDto {
  street?: string;
  homeNumber?: string = null;
  city: string;
  postalCode?: string = null;


  constructor(street: string, homeNumber: string, city: string, postalCode: string) {
    this.street = street;
    this.homeNumber = homeNumber;
    this.city = city;
    this.postalCode = postalCode;
  }
}

export class GeoLocationDto {
  latitude: number;
  longitude: number;


  constructor(latitude: number, longitude: number) {
    this.latitude = latitude;
    this.longitude = longitude;
  }
}

export class SocialMediaDto {
  webPageUrl?: string;
  facebookPageUrl?: string;
  facebookGroupUrl?: string;
  instagramUrl?: string;
  youTubeChannelUrl?: string;

  constructor(webPageUrl: string, facebookPageUrl: string, facebookGroupUrl: string, instagramUrl: string, youTubeChannelUrl: string) {
    this.webPageUrl = webPageUrl;
    this.facebookPageUrl = facebookPageUrl;
    this.facebookGroupUrl = facebookGroupUrl;
    this.instagramUrl = instagramUrl;
    this.youTubeChannelUrl = youTubeChannelUrl;
  }
}

export class ExtendedDescriptionDto {
  title?: string;
  content: string;

  constructor(title: string, content: string) {
    this.title = title;
    this.content = content;
  }
}

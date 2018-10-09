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
}

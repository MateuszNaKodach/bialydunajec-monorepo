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

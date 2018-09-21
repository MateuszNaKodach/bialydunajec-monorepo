export class SocialMediaLinkViewModel {
  name: string;
  url: string;
  customIconName: string;

  constructor(name: string, url: string, customIconName: string = null) {
    this.name = name;
    this.url = url;
    this.customIconName = customIconName ? customIconName : name;
  }
}

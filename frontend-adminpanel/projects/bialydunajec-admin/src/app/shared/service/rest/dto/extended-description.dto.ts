export class ExtendedDescriptionDto {
  title?: string;
  content: string;

  constructor(title: string = null, content: string = null) {
    this.title = title;
    this.content = content;
  }
}

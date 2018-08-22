export class ExtendedDescription {
  title: string;
  content: string;


  constructor(title: string, content: string) {
    this.title = title;
    this.content = content;
  }

  static empty() {
    return new ExtendedDescription('', '');
  }

  isEmpty() {
    return this.content == null || this.content.length === 0;
  }
}

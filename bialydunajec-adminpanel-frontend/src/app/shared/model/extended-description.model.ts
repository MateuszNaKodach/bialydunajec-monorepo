export class ExtendedDescription {
  title: string;
  content: string;

  constructor(title: string, content: string) {
    this.title = title;
    this.content = content;
  }

  isEmpty() {
    return this.content == null || this.content.length === 0;
  }
}

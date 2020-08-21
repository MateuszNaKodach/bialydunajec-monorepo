export class FaqQuestion {
  id: string;
  content: string;
  answer: string;

  constructor(id: string, content: string, answer: string) {
    this.id = id;
    this.content = content;
    this.answer = answer;
  }
}

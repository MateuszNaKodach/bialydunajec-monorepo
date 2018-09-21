import {FaqQuestion} from './faq-question.model';

export class FaqCategory {
  id: string;
  name: string;
  questions: FaqQuestion[];

  constructor(id: string, name: string, questions: FaqQuestion[] = []) {
    this.id = id;
    this.name = name;
    this.questions = questions;
  }
}

import {Component, Input, OnInit} from '@angular/core';
import {FaqCategory} from '../faq-category.model';
import {FaqQuestion} from '../faq-question.model';

@Component({
  selector: 'bda-faq-category',
  templateUrl: './faq-category.component.html',
  styleUrls: ['./faq-category.component.scss']
})
export class FaqCategoryComponent implements OnInit {
  @Input() private category: FaqCategory;

  constructor() {
  }

  ngOnInit() {
  }

  getQuestions(): FaqQuestion[] {
    return [...this.category.questions];
  }

  getCategoryName() {
    return this.category.name;
  }

}

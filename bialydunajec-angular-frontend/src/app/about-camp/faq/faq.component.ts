import {Component, OnInit, Output} from '@angular/core';
import {FaqCategory} from './faq-category.model';

@Component({
  selector: 'bda-faq',
  templateUrl: './faq.component.html',
  styleUrls: ['./faq.component.scss']
})
export class FaqComponent implements OnInit {

  @Output() categories: FaqCategory[] = [
    {
      name: 'Zapisy na obóz',
      questions: [
        {content: 'Kto może jechać na obóz?', answer: 'Answer'},
        {content: 'Czy trzeba być członkiem duszpasterstwa, żeby zapisać się na obóz?', answer: 'Answer'}
      ]
    },
    {
      name: 'Opłaty i inne kwestie finansowe',
      questions: [
        {content: 'Kto może jechać na obóz?', answer: 'Answer'},
        {content: 'Czy trzeba być członkiem duszpasterstwa, żeby zapisać się na obóz?', answer: 'Answer'},
        {content: 'Czy trzeba być członkiem duszpasterstwa, żeby zapisać się na obóz?', answer: 'Answer'},
        {content: 'Czy trzeba być członkiem duszpasterstwa, żeby zapisać się na obóz?', answer: 'Answer'},
        {content: 'Czy trzeba być członkiem duszpasterstwa, żeby zapisać się na obóz?', answer: 'Answer'}
      ]
    },
    {
      name: 'Warunki bytowe',
      questions: [
        {content: 'Kto może jechać na obóz?', answer: 'Answer'},
        {content: 'Czy trzeba być członkiem duszpasterstwa, żeby zapisać się na obóz?', answer: 'Answer'},
        {content: 'Czy trzeba być członkiem duszpasterstwa, żeby zapisać się na obóz?', answer: 'Answer'}
      ]
    },
    {
      name: 'Inne pytania',
      questions: [
        {content: 'Kto może jechać na obóz?', answer: 'Answer'},
        {content: 'Czy trzeba być członkiem duszpasterstwa, żeby zapisać się na obóz?', answer: 'Answer'},
        {content: 'Czy trzeba być członkiem duszpasterstwa, żeby zapisać się na obóz?', answer: 'Answer'},
        {content: 'Czy trzeba być członkiem duszpasterstwa, żeby zapisać się na obóz?', answer: 'Answer'}
      ]
    }
  ];

  constructor() {
  }

  ngOnInit() {
  }

}

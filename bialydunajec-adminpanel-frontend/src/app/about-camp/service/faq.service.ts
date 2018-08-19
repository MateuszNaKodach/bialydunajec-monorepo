import {Injectable} from '@angular/core';
import {FaqCategory} from '../model/faq-category.model';

@Injectable()
export class FaqService {

  private categories: FaqCategory[] = [
    {
      id: '1',
      name: 'Zapisy na obóz',
      questions: [
        {id: '1', content: 'Kto może jechać na obóz?', answer: 'Answer'},
        {id: '2', content: 'Czy trzeba być członkiem duszpasterstwa, żeby zapisać się na obóz?', answer: 'Answer'}
      ]
    },
    {
      id: '2',
      name: 'Opłaty i inne kwestie finansowe',
      questions: [
        {id: '1', content: 'Kto może jechać na obóz?', answer: 'Answer'},
        {id: '2', content: 'Czy trzeba być członkiem duszpasterstwa, żeby zapisać się na obóz?', answer: 'Answer'},
        {id: '3', content: 'Czy trzeba być członkiem duszpasterstwa, żeby zapisać się na obóz?', answer: 'Answer'},
        {id: '4', content: 'Czy trzeba być członkiem duszpasterstwa, żeby zapisać się na obóz?', answer: 'Answer'},
        {id: '5', content: 'Czy trzeba być członkiem duszpasterstwa, żeby zapisać się na obóz?', answer: 'Answer'}
      ]
    },
    {
      id: '3',
      name: 'Warunki bytowe',
      questions: [
        {id: '1', content: 'Kto może jechać na obóz?', answer: 'Answer'},
        {id: '2', content: 'Czy trzeba być członkiem duszpasterstwa, żeby zapisać się na obóz?', answer: 'Answer'},
        {id: '3', content: 'Czy trzeba być członkiem duszpasterstwa, żeby zapisać się na obóz?', answer: 'Answer'}
      ]
    },
    {
      id: '4',
      name: 'Inne pytania',
      questions: [
        {id: '1', content: 'Kto może jechać na obóz?', answer: 'Answer'},
        {id: '2', content: 'Czy trzeba być członkiem duszpasterstwa, żeby zapisać się na obóz?', answer: 'Answer'},
        {id: '3', content: 'Czy trzeba być członkiem duszpasterstwa, żeby zapisać się na obóz?', answer: 'Answer'},
        {id: '4', content: 'Czy trzeba być członkiem duszpasterstwa, żeby zapisać się na obóz?', answer: 'Answer'}
      ]
    }
  ];

  constructor() {
  }

  getCategories() {
    return this.categories.slice();
  }
}

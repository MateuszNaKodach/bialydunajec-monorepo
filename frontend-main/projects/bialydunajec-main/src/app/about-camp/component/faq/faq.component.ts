import {Component, OnInit, Output} from '@angular/core';
import {FaqCategory} from '../../model/faq-category.model';
import {FaqService} from '../../service/faq.service';

@Component({
  selector: 'bda-faq',
  templateUrl: './faq.component.html',
  styleUrls: ['./faq.component.scss']
})
export class FaqComponent implements OnInit {

  @Output() categories: FaqCategory[] = [];

  constructor(private faqService: FaqService) {
  }

  ngOnInit() {
    this.categories = this.faqService.getCategories();
  }

}

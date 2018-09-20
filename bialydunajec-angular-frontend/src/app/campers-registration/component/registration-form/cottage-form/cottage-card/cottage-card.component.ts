import {Component, Input, OnInit} from '@angular/core';
import {CottageCardViewModel} from './cottage-card.view-model';

@Component({
  selector: 'bda-cottage-card',
  templateUrl: './cottage-card.component.html',
  styleUrls: ['./cottage-card.component.scss']
})
export class CottageCardComponent implements OnInit {

  @Input() cottageViewModel: CottageCardViewModel;
  hover = false;
  selected = false;

  constructor() {
  }

  ngOnInit() {
  }

}

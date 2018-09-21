import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CottageCardViewModel} from './cottage-card.view-model';

@Component({
  selector: 'bda-cottage-card',
  templateUrl: './cottage-card.component.html',
  styleUrls: ['./cottage-card.component.scss']
})
export class CottageCardComponent implements OnInit {

  @Input() cottageViewModel: CottageCardViewModel;
  @Input() selected = false;

  constructor() {
  }

  ngOnInit() {
  }


}

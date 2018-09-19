import {Component, OnInit} from '@angular/core';
import {CottageCardViewModel} from './cottage-card.view-model';

@Component({
  selector: 'bda-cottage-card',
  templateUrl: './cottage-card.component.html',
  styleUrls: ['./cottage-card.component.scss']
})
export class CottageCardComponent implements OnInit {

  viewModel: CottageCardViewModel = {
    cottageId: '1',
    cottageName: 'Maciejówka',
    cottageLogoUrl: 'http://bialydunajec.org:3344/api/v1/academic-ministry/18/logo'
  };
  hover = false;

  constructor() {
  }

  ngOnInit() {
  }

}

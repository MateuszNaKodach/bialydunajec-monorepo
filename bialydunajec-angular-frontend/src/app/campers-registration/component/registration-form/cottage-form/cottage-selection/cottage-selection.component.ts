import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'bda-cottage-selection',
  templateUrl: './cottage-selection.component.html',
  styleUrls: ['./cottage-selection.component.scss']
})
export class CottageSelectionComponent implements OnInit {

  cottages = [
    {
      cottageId: '1',
      cottageName: 'Antoni',
      cottageLogoUrl: 'http://bialydunajec.org:3344/api/v1/academic-ministry/3/logo',
      hasSpace: true
    },
    {
      cottageId: '2',
      cottageName: 'Dach',
      cottageLogoUrl: 'http://bialydunajec.org:3344/api/v1/academic-ministry/6/logo',
      hasSpace: false
    },
    {
      cottageId: '3',
      cottageName: 'Dominik',
      cottageLogoUrl: 'http://bialydunajec.org:3344/api/v1/academic-ministry/9/logo',
      hasSpace: true
    },
    {
      cottageId: '4',
      cottageName: 'Maciejówka',
      cottageLogoUrl: 'http://bialydunajec.org:3344/api/v1/academic-ministry/18/logo',
      hasSpace: false
    },
    {
      cottageId: '5',
      cottageName: 'Redemptor',
      cottageLogoUrl: 'http://bialydunajec.org:3344/api/v1/academic-ministry/21/logo',
      hasSpace: false
    },
    {
      cottageId: '4',
      cottageName: 'Maciejówka',
      cottageLogoUrl: 'http://bialydunajec.org:3344/api/v1/academic-ministry/18/logo',
      hasSpace: false
    }
  ];

  constructor() {
  }

  ngOnInit() {
  }

}

import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Cottage} from './cottage.model';

@Component({
  selector: 'bda-cottages-names-list',
  templateUrl: './cottages-names-list.component.html',
  styleUrls: ['./cottages-names-list.component.scss']
})
export class CottagesNamesListComponent implements OnInit {

  @Output() cottageSelected = new EventEmitter<Cottage>();

  cottages: Cottage[] = [
    {id: '1', shortName: 'Antoni'},
    {id: '2', shortName: 'Dach'},
    {id: '3', shortName: 'Dominik'},
    {id: '4', shortName: 'Góra Św. Anny'},
    {id: '5', shortName: 'Maciejówka'},
    {id: '6', shortName: 'Most'},
    {id: '21', shortName: 'Redemptor'},
    {id: '23', shortName: 'Wawrzyny'}
  ];

  constructor() {
  }

  ngOnInit() {
  }

  onCottageSelected(cottage: Cottage) {
    this.cottageSelected.emit(cottage);
  }

}

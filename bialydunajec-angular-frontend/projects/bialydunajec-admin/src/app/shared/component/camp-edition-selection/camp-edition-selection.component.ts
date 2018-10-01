import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'bda-admin-camp-edition-selection',
  templateUrl: './camp-edition-selection.component.html',
  styleUrls: ['./camp-edition-selection.component.less']
})
export class CampEditionSelectionComponent implements OnInit {

  selectedEdition = 'XXXVI';

  constructor() {
  }

  ngOnInit() {
  }

}

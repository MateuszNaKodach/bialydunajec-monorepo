import {Component, Input, OnInit} from '@angular/core';
import {AcademicMinistryCard} from './academic-ministry-card.model';

@Component({
  selector: 'bda-academic-ministry-card',
  templateUrl: './academic-ministry-card.component.html',
  styleUrls: ['./academic-ministry-card.component.scss']
})
export class AcademicMinistryCardComponent implements OnInit {

  @Input() academicMinistry: AcademicMinistryCard;

  constructor() {
  }

  ngOnInit() {
  }

}

import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AcademicMinistryCard} from './academic-ministry-card.model';

@Component({
  selector: 'bda-academic-ministry-card',
  templateUrl: './academic-ministry-card.component.html',
  styleUrls: ['./academic-ministry-card.component.scss']
})
export class AcademicMinistryCardComponent implements OnInit {

  @Output() click = new EventEmitter<void>();

  @Input() academicMinistry: AcademicMinistryCard;

  constructor() {
  }

  ngOnInit() {
  }

  onClick() {
    this.click.emit();
  }

}

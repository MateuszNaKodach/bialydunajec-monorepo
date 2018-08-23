import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AcademicMinistryCardViewModel} from './academic-ministry-card.view-model';

@Component({
  selector: 'bda-academic-ministry-card',
  templateUrl: './academic-ministry-card.component.html',
  styleUrls: ['./academic-ministry-card.component.scss']
})
export class AcademicMinistryCardComponent implements OnInit {

  @Output() click = new EventEmitter<void>();

  @Input() academicMinistry: AcademicMinistryCardViewModel;

  constructor() {
  }

  ngOnInit() {
  }

  onClick() {
    this.click.emit();
  }

}

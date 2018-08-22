import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {AcademicMinistryCard} from '../academic-ministry-card/academic-ministry-card.model';

@Component({
  selector: 'bda-academic-ministry-selection',
  templateUrl: './academic-ministry-selection.component.html',
  styleUrls: ['./academic-ministry-selection.component.scss']
})
export class AcademicMinistrySelectionComponent implements OnInit {

  @Output() ministrySelected = new EventEmitter<{ id: string, name: string }>();

  academicMinistries: AcademicMinistryCard[] = [
    {id: '21', name: 'Redemptor', logoUrl: 'http://bialydunajec.org:3344/api/v1/academic-ministry/21/logo'},
    {id: '21', name: 'Redemptor', logoUrl: 'http://bialydunajec.org:3344/api/v1/academic-ministry/21/logo'},
    {id: '21', name: 'Redemptor', logoUrl: 'http://bialydunajec.org:3344/api/v1/academic-ministry/21/logo'},
    {id: '21', name: 'Redemptor', logoUrl: 'http://bialydunajec.org:3344/api/v1/academic-ministry/21/logo'},
    {id: '21', name: 'Redemptor', logoUrl: 'http://bialydunajec.org:3344/api/v1/academic-ministry/21/logo'},
    {id: '21', name: 'Redemptor', logoUrl: 'http://bialydunajec.org:3344/api/v1/academic-ministry/21/logo'},
    {id: '21', name: 'Redemptor', logoUrl: 'http://bialydunajec.org:3344/api/v1/academic-ministry/21/logo'},
    {id: '21', name: 'Redemptor', logoUrl: 'http://bialydunajec.org:3344/api/v1/academic-ministry/21/logo'},
    {id: '21', name: 'Redemptor', logoUrl: 'http://bialydunajec.org:3344/api/v1/academic-ministry/21/logo'},
    {id: '21', name: 'Redemptor', logoUrl: 'http://bialydunajec.org:3344/api/v1/academic-ministry/21/logo'}
  ];

  constructor() {
  }

  ngOnInit() {
  }

  onClick(academicMinistry: AcademicMinistryCard) {
    this.ministrySelected.emit({id: academicMinistry.id, name: academicMinistry.name});
  }

}

import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {AcademicMinistryCardViewModel} from '../academic-ministry-card/academic-ministry-card.view-model';
import {AcademicMinistryService} from '../../service/academic-ministry.service';

@Component({
  selector: 'bda-academic-ministry-selection',
  templateUrl: './academic-ministry-selection.component.html',
  styleUrls: ['./academic-ministry-selection.component.scss']
})
export class AcademicMinistrySelectionComponent implements OnInit {

  @Output() ministrySelected = new EventEmitter<{ id: string, name: string }>();

  academicMinistries: AcademicMinistryCardViewModel[] = [];

  constructor(private academicMinistryService: AcademicMinistryService) {
  }

  ngOnInit() {
    this.academicMinistries = this.academicMinistryService.getAllAcademicMinistry()
      .map(academicMinistry => {
        return {id: academicMinistry.id, name: academicMinistry.shortName, logoUrl: academicMinistry.logoUrl};
      });
  }

  onClick(academicMinistry: AcademicMinistryCardViewModel) {
    this.ministrySelected.emit({id: academicMinistry.id, name: academicMinistry.name});
  }

}

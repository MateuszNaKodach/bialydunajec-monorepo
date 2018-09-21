import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {AcademicMinistryService} from '../../service/academic-ministry.service';
import {AcademicMinistry} from '../../model/academic-ministry.model';

@Component({
  selector: 'bda-academic-ministries-names',
  templateUrl: './academic-ministries-names.component.html',
  styleUrls: ['./academic-ministries-names.component.scss']
})
export class AcademicMinistriesNamesComponent implements OnInit {

  @Output() ministrySelected = new EventEmitter<AcademicMinistry>();

  academicMinistries: AcademicMinistry[] = [];

  constructor(private academicMinistryService: AcademicMinistryService) {
  }

  ngOnInit() {
    this.academicMinistries = this.academicMinistryService.getAllAcademicMinistry();
  }

  onAcademicMinistrySelected(academicMinistry: AcademicMinistry) {
    this.ministrySelected.emit(academicMinistry);
  }

}

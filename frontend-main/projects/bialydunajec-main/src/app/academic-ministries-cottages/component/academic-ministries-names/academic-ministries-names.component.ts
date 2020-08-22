import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {AcademicMinistryService} from '../../service/academic-ministry.service';
import {AcademicMinistry} from '../../model/academic-ministry.model';
import {AcademicMinistryEndpoint} from '../../service/rest/academic-ministry.endpoint';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';

@Component({
  selector: 'bda-academic-ministries-names',
  templateUrl: './academic-ministries-names.component.html',
  styleUrls: ['./academic-ministries-names.component.scss']
})
export class AcademicMinistriesNamesComponent implements OnInit {

  @Output() ministrySelected = new EventEmitter<AcademicMinistry>();

  academicMinistries$: Observable<AcademicMinistry[]>;

  constructor(
    private academicMinistryService: AcademicMinistryService,
    private academicMinistryEndpoint: AcademicMinistryEndpoint) {
  }

  ngOnInit() {
    this.academicMinistries$ = this.academicMinistryEndpoint.getAllAcademicMinistriesNames()
      .pipe(
        map(it => it.map(ministry => new AcademicMinistry(ministry.academicMinistryId, ministry.officialName, ministry.shortName, null)))
      );
  }

  onAcademicMinistrySelected(academicMinistry: AcademicMinistry) {
    this.ministrySelected.emit(academicMinistry);
  }

}

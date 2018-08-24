import {Component, OnInit, Output} from '@angular/core';
import {AcademicMinistryService} from '../../service/academic-ministry.service';
import {AcademicMinistryDetails} from '../../model/academic-ministry-details.model';
import {ActivatedRoute, Params} from '@angular/router';
import {academicMinistriesCottagesPaths} from '../../academic-ministries-cottages.paths';

@Component({
  selector: 'bda-academic-ministry-details',
  templateUrl: './academic-ministry-details.component.html',
  styleUrls: ['./academic-ministry-details.component.scss']
})
export class AcademicMinistryDetailsComponent implements OnInit {

  academicMinistry: AcademicMinistryDetails;

  constructor(private activatedRoute: ActivatedRoute, private academicMinistryService: AcademicMinistryService) {
  }

  ngOnInit() {
    this.activatedRoute.parent.params
      .subscribe(
        (params: Params) => {
          const academicMinistryId = params[academicMinistriesCottagesPaths.academicMinistryId];
          this.academicMinistry = this.academicMinistryService.getAcademicMinistryDetailsById(academicMinistryId);
        }
      );
  }

}

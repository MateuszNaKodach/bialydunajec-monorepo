import {Component, OnInit, Output} from '@angular/core';
import {AcademicMinistryService} from '../../service/academic-ministry.service';
import {AcademicMinistryDetails} from '../../model/academic-ministry-details.model';
import {ActivatedRoute, Params} from '@angular/router';
import {academicMinistriesCottagesRoutingPaths} from '../../academic-ministries-cottages-routing.paths';


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
          const academicMinistryId = params[academicMinistriesCottagesRoutingPaths.academicMinistryId];
          this.academicMinistry = this.academicMinistryService.getAcademicMinistryDetailsById(academicMinistryId);
        }
      );
  }

}

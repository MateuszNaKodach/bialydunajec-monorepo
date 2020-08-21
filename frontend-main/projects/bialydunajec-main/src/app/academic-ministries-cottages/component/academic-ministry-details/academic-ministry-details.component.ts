import {Component, OnInit} from '@angular/core';
import {AcademicMinistryService} from '../../service/academic-ministry.service';
import {ActivatedRoute} from '@angular/router';
import {flatMap, tap} from 'rxjs/operators';
import {AcademicMinistryEndpoint} from '../../service/rest/academic-ministry.endpoint';
import {AcademicMinistryResponse} from '../../../campers-registration/service/rest/response/academic-ministry.response';
import {Observable} from 'rxjs';
import {AcademicPriestDto} from '../../service/rest/dto/academic-priest.dto';


@Component({
  selector: 'bda-academic-ministry-details',
  templateUrl: './academic-ministry-details.component.html',
  styleUrls: ['./academic-ministry-details.component.scss']
})
export class AcademicMinistryDetailsComponent implements OnInit {

  //academicMinistry: AcademicMinistryDetails;
  academicMinistry$: Observable<AcademicMinistryResponse>;
  academicPriests$: Observable<AcademicPriestDto[]>;

  constructor(
    private activatedRoute: ActivatedRoute,
    private academicMinistryService: AcademicMinistryService,
    private academicMinistryEndpoint: AcademicMinistryEndpoint) {
  }

  ngOnInit() {
    /*
    this.activatedRoute.parent.params
      .subscribe(
        (params: Params) => {
          const academicMinistryId = params[academicMinistriesCottagesRoutingPaths.academicMinistryId];
          this.academicMinistry = this.academicMinistryService.getAcademicMinistryDetailsById(academicMinistryId);
        }
      );
      */
    this.academicMinistry$ = this.activatedRoute.parent.params
      .pipe(
        flatMap(params => this.academicMinistryEndpoint.getAcademicMinistryById(params['academicMinistryId'])),
        tap(academicMinistry => this.academicPriests$ = this.academicMinistryEndpoint.getAllAcademicPriestByAcademicMinistryId(academicMinistry.academicMinistryId))
      );
  }

}

import {Component, OnInit, Output} from '@angular/core';
import {AcademicMinistryService} from '../../service/academic-ministry.service';
import {AcademicMinistryDetails} from '../../model/academic-ministry-details.model';
import {ActivatedRoute, Params} from '@angular/router';
import {academicMinistriesCottagesRoutingPaths} from '../../academic-ministries-cottages-routing.paths';
import {flatMap, map, tap} from 'rxjs/operators';
import {AcademicMinistryEndpoint} from '../../service/rest/academic-ministry.endpoint';
import {Facebook} from '../../../shared/model/facebook.model';
import {Address} from '../../../shared/model/address.model';
import {AcademicMinistryResponse} from '../../../../../../bialydunajec-admin/src/app/academic-ministry/service/rest/response/academic-ministry.response';
import {Observable} from 'rxjs';
import {AcademicPriestDto} from '../../../../../../bialydunajec-admin/src/app/academic-ministry/service/rest/dto/academic-priest.dto';


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

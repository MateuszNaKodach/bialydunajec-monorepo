import {Component, OnInit} from '@angular/core';
import {CottageService} from '../../service/cottage.service';
import {ActivatedRoute, Params} from '@angular/router';
import {academicMinistriesCottagesRoutingPaths} from '../../academic-ministries-cottages-routing.paths';
import {CottageDetails} from '../../model/cottage-details.model';
import {CottageEndpoint} from '../../service/rest/cottage.endpoint';
import {CottageInfoDto} from '../../service/rest/dto/cottage-info.dto';
import {flatMap, tap} from 'rxjs/operators';
import {Observable} from 'rxjs';

@Component({
  selector: 'bda-academic-ministry-cottage',
  templateUrl: './academic-ministry-cottage.component.html',
  styleUrls: ['./academic-ministry-cottage.component.scss']
})
export class AcademicMinistryCottageComponent implements OnInit {

  dateFormat = 'DD.MM.YYYY';
  //cottage: CottageDetails;
  cottage$: Observable<CottageInfoDto>;

  constructor(private activatedRoute: ActivatedRoute,
              private cottageService: CottageService,
              private cottageEndpoint: CottageEndpoint) {
  }

  ngOnInit() {
    /*this.activatedRoute.parent.params
      .subscribe(
        (params: Params) => {
          console.log('Params:', params);
          const academicMinistryId = params[academicMinistriesCottagesRoutingPaths.academicMinistryId];
          console.log('Academic: ', academicMinistryId);
          this.cottage = this.cottageService.getCottageByAcademicMinistryId(academicMinistryId);
        }
      );*/
    this.cottage$ = this.activatedRoute.parent.params
      .pipe(
        flatMap(params => this.cottageEndpoint.getNewestCottageByAcademicMinistryId(params['academicMinistryId']))
      );
  }

}

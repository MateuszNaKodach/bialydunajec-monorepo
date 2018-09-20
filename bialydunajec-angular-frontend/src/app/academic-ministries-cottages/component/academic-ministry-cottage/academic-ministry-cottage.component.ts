import {Component, OnInit} from '@angular/core';
import {CottageService} from '../../service/cottage.service';
import {ActivatedRoute, Params} from '@angular/router';
import {academicMinistriesCottagesRoutingPaths} from '../../academic-ministries-cottages-routing.paths';
import {CottageDetails} from '../../model/cottage-details.model';

@Component({
  selector: 'bda-academic-ministry-cottage',
  templateUrl: './academic-ministry-cottage.component.html',
  styleUrls: ['./academic-ministry-cottage.component.scss']
})
export class AcademicMinistryCottageComponent implements OnInit {

  cottage: CottageDetails;

  constructor(private activatedRoute: ActivatedRoute, private cottageService: CottageService) {
  }

  ngOnInit() {
    this.activatedRoute.parent.params
      .subscribe(
        (params: Params) => {
          console.log('Params:', params);
          const academicMinistryId = params[academicMinistriesCottagesRoutingPaths.academicMinistryId];
          console.log('Academic: ', academicMinistryId);
          this.cottage = this.cottageService.getCottageByAcademicMinistryId(academicMinistryId);
        }
      );
  }

}

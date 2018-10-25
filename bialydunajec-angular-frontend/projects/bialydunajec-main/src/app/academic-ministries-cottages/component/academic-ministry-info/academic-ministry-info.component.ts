import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {OptionSelected} from '../../../shared/component/dual-toggle-button/event/option-selected.event';
import {AcademicMinistryService} from '../../service/academic-ministry.service';
import {AcademicMinistry} from '../../model/academic-ministry.model';
import {academicMinistriesCottagesRoutingPaths} from '../../academic-ministries-cottages-routing.paths';
import {Observable} from 'rxjs';
import {AcademicMinistryResponse} from '../../../../../../bialydunajec-admin/src/app/academic-ministry/service/rest/response/academic-ministry.response';
import {AcademicMinistryEndpoint} from '../../service/rest/academic-ministry.endpoint';
import {flatMap, tap} from 'rxjs/operators';

const TAB_COTTAGE = 'Chatka';
const TAB_MINISTRY = 'Duszpasterstwo';

@Component({
  selector: 'bda-academic-ministry-info',
  templateUrl: './academic-ministry-info.component.html',
  styleUrls: ['./academic-ministry-info.component.scss']
})
export class AcademicMinistryInfoComponent implements OnInit {

  tabs = {
    left: {
      name: TAB_COTTAGE,
      icon: 'home'
    },
    right: {
      name: TAB_MINISTRY,
      icon: 'users'
    }
  };

  //academicMinistry: AcademicMinistry;
  academicMinistry$: Observable<AcademicMinistryResponse>;


  constructor(private router: Router,
              private activatedRoute: ActivatedRoute,
              private academicMinistryService: AcademicMinistryService,
              private academicMinistryEndpoint: AcademicMinistryEndpoint) {
  }

  ngOnInit() {
    /*
    this.activatedRoute.params
      .subscribe(
        (params: Params) => {
          const academicMinistryId = params[academicMinistriesCottagesRoutingPaths.academicMinistryId];
          this.academicMinistry = this.academicMinistryService.getAcademicMinistryById(academicMinistryId);
        }
      );
      */
    this.academicMinistry$ = this.activatedRoute.params
      .pipe(
        flatMap(params =>
          this.academicMinistryEndpoint.getAcademicMinistryById(params[academicMinistriesCottagesRoutingPaths.academicMinistryId])),
        tap(r => console.log('RESPONSE', r))
      );
  }

  isCottageTab(): boolean {
    return this.router.url.indexOf(TAB_COTTAGE.toLowerCase()) !== -1;
  }

  isAcademicMinistryTab(): boolean {
    return this.router.url.indexOf(TAB_MINISTRY.toLowerCase()) !== -1;
  }

  getSelectedTabSide() {
    if (this.isCottageTab()) {
      return 'left';
    } else if (this.isAcademicMinistryTab()) {
      return 'right';
    } else {
      return '';
    }
  }

  onTabSelected($event: OptionSelected) {
    const selectedOptionName = $event.name;
    console.log(selectedOptionName);
    this.router.navigate([selectedOptionName.toLowerCase()], {relativeTo: this.activatedRoute});
  }

}

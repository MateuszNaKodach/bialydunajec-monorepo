import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {OptionSelected} from '../../../shared/component/dual-toggle-button/event/option-selected.event';
import {AcademicMinistryService} from '../../service/academic-ministry.service';
import {AcademicMinistry} from '../../model/academic-ministry.model';
import {academicMinistriesCottagesPaths} from '../../academic-ministries-cottages.paths';

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
      icon: 'user'
    },
    right: {
      name: TAB_MINISTRY,
      icon: 'blind'
    }
  };

  academicMinistry: AcademicMinistry;

  constructor(private router: Router,
              private activatedRoute: ActivatedRoute,
              private academicMinistryService: AcademicMinistryService) {
  }

  ngOnInit() {
    this.activatedRoute.params
      .subscribe(
        (params: Params) => {
          const academicMinistryId = params[academicMinistriesCottagesPaths.academicMinistryId];
          this.academicMinistry = this.academicMinistryService.getAcademicMinistryById(academicMinistryId);
        }
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

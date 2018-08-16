import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {OptionSelected} from '../../shared/dual-toggle-button/event/option-selected.event';

const TAB_COTTAGE = 'Chatka';
const TAB_MINISTRY = 'Duszpasterstwo';

@Component({
  selector: 'bda-cottage',
  templateUrl: './cottage.component.html',
  styleUrls: ['./cottage.component.scss']
})
export class CottageComponent implements OnInit {

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

  constructor(private router: Router, private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
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

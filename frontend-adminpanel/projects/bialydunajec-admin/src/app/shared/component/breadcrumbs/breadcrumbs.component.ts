import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'bda-admin-breadcrumbs',
  templateUrl: './breadcrumbs.component.html',
  styleUrls: ['./breadcrumbs.component.less']
})
export class BreadcrumbsComponent implements OnInit {

  @Input() breadcrumbs: string[];

  constructor() { }

  ngOnInit() {
  }

}

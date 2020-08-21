import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'bda-admin-panel-section',
  templateUrl: './panel-section.component.html',
  styleUrls: ['./panel-section.component.less']
})
export class PanelSectionComponent implements OnInit {

  @Input() breadcrumbs: string[];

  constructor() {
  }

  ngOnInit() {
  }

}

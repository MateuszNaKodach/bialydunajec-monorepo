import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {coreRoutingPaths} from '../../core-routing.paths';
import {campEditionRoutingPaths} from '../../../camp-edition/camp-edition-routing.paths';
import {academicMinistryRoutingPaths} from '../../../academic-ministry/academic-ministry.routing.paths';
import {campRegistrationsRoutingPaths} from '../../../camp-registrations/camp-registrations-routing.paths';

@Component({
  selector: 'bda-admin-panel-layout',
  templateUrl: './panel-layout.component.html',
  styleUrls: ['./panel-layout.component.less']
})
export class PanelLayoutComponent implements OnInit {

  coreRoutingPaths = coreRoutingPaths;
  academicMinistryRoutingPaths = academicMinistryRoutingPaths;
  campEditionRoutingPaths = campEditionRoutingPaths;
  campRegistrationsRoutingPaths = campRegistrationsRoutingPaths;

  isCollapsed = false;
  triggerTemplate = null;
  @ViewChild('trigger') customTrigger: TemplateRef<void>;

  constructor() {
  }

  ngOnInit() {
  }

  /** custom trigger can be TemplateRef **/
  changeTrigger(): void {
    this.triggerTemplate = this.customTrigger;
  }

}

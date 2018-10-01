import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';

@Component({
  selector: 'bda-admin-panel-layout',
  templateUrl: './panel-layout.component.html',
  styleUrls: ['./panel-layout.component.less']
})
export class PanelLayoutComponent implements OnInit {

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

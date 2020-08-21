import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'bda-admin-cottage-status-badge',
  template: `
    <label
      class="bda-admin-cottage-status-label"
      [ngClass]="{'configured': cottageStatus === 'CONFIGURED', 'unconfigured': cottageStatus === 'UNCONFIGURED','activated': cottageStatus === 'ACTIVATED'}">
      {{getCottageStatusInPolish(cottageStatus)}}</label>
  `,
  styles: [`
    .bda-admin-cottage-status-label {
      border: 2px solid;
      padding: 1px 10px 3px 10px;
      border-radius: 4px;
      line-height: 18px;
      display: inline;
      font-weight: 700;
      text-transform: uppercase;
    }

    .configured {
      border-color: #1890ff;
      color: #1890ff;
    }

    .unconfigured {
      border-color: #c30000;
      color: #c30000;
    }

    .activated {
      border-color: #85c020;
      color: #85c020;
    }
  `]
})
export class CottageStatusBadgeComponent implements OnInit {

  @Input() cottageStatus: 'UNCONFIGURED' | 'CONFIGURED' | 'ACTIVATED';

  constructor() {
  }

  ngOnInit() {
  }

  getCottageStatusInPolish(status: string) {
    switch (status) {
      case 'UNCONFIGURED': {
        return 'NIESKONFIGUROWANA';
      }
      case 'CONFIGURED': {
        return 'SKONFIGUROWANA';
      }
      case 'ACTIVATED': {
        return 'AKTYWNA';
      }
    }
  }

}

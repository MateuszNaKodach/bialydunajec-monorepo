import {Component, Input, OnInit} from '@angular/core';
import {CottageResponse} from '../../../service/rest/response/cottage.response';
import {campRegistrationsRoutingPaths} from '../../../camp-registrations-routing.paths';

@Component({
  selector: 'bda-admin-cottage-card',
  templateUrl: './cottage-card.component.html',
  styleUrls: ['./cottage-card.component.less']
})
export class CottageCardComponent implements OnInit {

  campRegistrationsRoutingPaths = campRegistrationsRoutingPaths;
  @Input() cottage: CottageResponse;

  constructor() {
  }

  ngOnInit() {
  }

}

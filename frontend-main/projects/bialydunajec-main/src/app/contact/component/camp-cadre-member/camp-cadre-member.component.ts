import {Component, Input, OnInit} from '@angular/core';
import {CadreMember} from '../../model/cadre-member.model';

@Component({
  selector: 'bda-camp-cadre-member',
  templateUrl: './camp-cadre-member.component.html',
  styleUrls: ['./camp-cadre-member.component.scss']
})
export class CampCadreMemberComponent implements OnInit {

  @Input() cadreMember: CadreMember;

  constructor() {
  }

  ngOnInit() {
  }

}

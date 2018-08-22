import {Component, OnInit, Output} from '@angular/core';
import {CadreMember} from '../../model/cadre-member.model';
import {CampCadreService} from '../../service/camp-cadre.service';

@Component({
  selector: 'bda-camp-cadre-contact',
  templateUrl: './camp-cadre-contact.component.html',
  styleUrls: ['./camp-cadre-contact.component.scss']
})
export class CampCadreContactComponent implements OnInit {

  @Output() campCadreMembers: CadreMember[];

  constructor(private campCadreService: CampCadreService) {
  }

  ngOnInit() {
    this.campCadreMembers = this.campCadreService.getCampCadreMembers();
  }

}

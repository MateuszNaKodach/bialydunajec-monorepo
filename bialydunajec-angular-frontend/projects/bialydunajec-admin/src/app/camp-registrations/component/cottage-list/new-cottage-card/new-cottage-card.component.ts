import {Component, OnInit} from '@angular/core';
import {coreRoutingPaths} from '../../../../core/core-routing.paths';
import {campRegistrationsRoutingPaths} from '../../../camp-registrations-routing.paths';
import {CottageType} from '../../../../shared/model/CottageType.enum';
import {CampRegistrationsEndpoint} from '../../../service/rest/camp-registrations.endpoint';
import {Observable} from 'rxjs';
import {CampEditionResponse} from '../../../../camp-edition/service/rest/response/camp-edition.response';
import {AcademicMinistryResponse} from '../../../service/rest/response/academic-ministry.response';
import {FormGroup, FormGroupDirective} from '@angular/forms';

@Component({
  selector: 'bda-admin-new-cottage-card',
  templateUrl: './new-cottage-card.component.html',
  styleUrls: ['./new-cottage-card.component.less']
})
export class NewCottageCardComponent implements OnInit {
  coreRoutingPaths = coreRoutingPaths;
  campRegistrationsRoutingPaths = campRegistrationsRoutingPaths;

  cottageType: CottageType;
  availableAcademicMinistries: Observable<AcademicMinistryResponse[]>;
  newCottageForm: FormGroup;

  constructor(private campRegistrationsEndpoint: CampRegistrationsEndpoint) {
  }

  ngOnInit() {
    this.availableAcademicMinistries = this.campRegistrationsEndpoint.getAllAcademicMinistries();
  }

}

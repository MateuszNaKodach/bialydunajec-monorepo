import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {coreRoutingPaths} from '../../core-routing.paths';
import {campEditionRoutingPaths} from '../../../camp-edition/camp-edition-routing.paths';
import {academicMinistryRoutingPaths} from '../../../academic-ministry/academic-ministry.routing.paths';
import {campRegistrationsRoutingPaths} from '../../../camp-registrations/camp-registrations-routing.paths';
import {Observable} from 'rxjs';
import {AcademicMinistryResponse} from '../../../academic-ministry/service/rest/response/academic-ministry.response';
import {AcademicMinistryAdminEndpoint} from '../../../academic-ministry/service/rest/academic-ministry.endpoint';
import {emailMessageRoutingPaths} from '../../../email-message/email-message-routing.paths';

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
  emailMessageRoutingPaths = emailMessageRoutingPaths;

  academicMinistries: Observable<AcademicMinistryResponse[]>;

  isCollapsed = false;
  triggerTemplate = null;
  @ViewChild('trigger') customTrigger: TemplateRef<void>;

  constructor(private academicMinistryEndpoint: AcademicMinistryAdminEndpoint) {
  }

  ngOnInit() {
    this.updateAcademicMinistriesList();
  }

  onMenuOpenChange(menuOpen: boolean) {
    if (menuOpen) {
      this.updateAcademicMinistriesList();
    }
  }

  updateAcademicMinistriesList() {
    this.academicMinistries = this.academicMinistryEndpoint.getAllAcademicMinistries();
  }

  /** custom trigger can be TemplateRef **/
  changeTrigger(): void {
    this.triggerTemplate = this.customTrigger;
  }

}

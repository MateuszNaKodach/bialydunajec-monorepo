import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {coreRoutingPaths} from '../../../../core/core-routing.paths';
import {campRegistrationsRoutingPaths} from '../../../camp-registrations-routing.paths';
import {CottageType} from '../../../../shared/model/CottageType.enum';
import {CampRegistrationsEndpoint} from '../../../service/rest/camp-registrations.endpoint';
import {Observable} from 'rxjs';
import {AcademicMinistryResponse} from '../../../service/rest/response/academic-ministry.response';
import {FormBuilder} from '@angular/forms';

@Component({
  selector: 'bda-admin-new-cottage-card',
  templateUrl: './new-cottage-card.component.html',
  styleUrls: ['./new-cottage-card.component.less']
})
export class NewCottageCardComponent implements OnInit {
  coreRoutingPaths = coreRoutingPaths;
  campRegistrationsRoutingPaths = campRegistrationsRoutingPaths;

  private _cottageType: CottageType = CottageType.ACADEMIC_MINISTRY;
  standaloneCottageName: string;
  selectedAcademicMinistryId: string;
  availableAcademicMinistries: Observable<AcademicMinistryResponse[]>;
  @Output() submittedNewAcademicMinistryCottage = new EventEmitter<{ selectedAcademicMinistryId: string }>();
  @Output() submittedNewStandaloneCottage = new EventEmitter<{ cottageName: string }>();


  constructor(
    private formBuilder: FormBuilder,
    private campRegistrationsEndpoint: CampRegistrationsEndpoint) {
  }

  ngOnInit() {
    this.availableAcademicMinistries = this.campRegistrationsEndpoint.getAllAcademicMinistries();
  }

  onSubmit() {
    console.log('ON SUBMIT!');
    switch (this._cottageType.toString()) {
      case 'STANDALONE': {
        this.submittedNewStandaloneCottage.emit({
          cottageName: this.standaloneCottageName
        });
        break;
      }
      case 'ACADEMIC_MINISTRY': {
        this.submittedNewAcademicMinistryCottage.emit({
          selectedAcademicMinistryId: this.selectedAcademicMinistryId
        });
        break;
      }
    }
  }

  onAcademicMinistryIdSelected(academicMinistryId: string) {
    this.selectedAcademicMinistryId = academicMinistryId;
  }

  get cottageType(): CottageType {
    return this._cottageType;
  }

  set cottageType(value: CottageType) {
    this.selectedAcademicMinistryId = null;
    this.standaloneCottageName = null;
    this._cottageType = value;
  }

}

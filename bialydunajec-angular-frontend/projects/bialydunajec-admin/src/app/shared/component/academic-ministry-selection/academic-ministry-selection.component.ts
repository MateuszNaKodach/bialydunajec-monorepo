import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AcademicMinistryResponse} from '../../../academic-ministry/service/rest/response/academic-ministry.response';
import {Observable, Subscription} from 'rxjs';

@Component({
  selector: 'bda-admin-academic-ministry-selection',
  templateUrl: './academic-ministry-selection.component.html',
  styleUrls: ['./academic-ministry-selection.component.css']
})
export class AcademicMinistrySelectionComponent implements OnInit {

  private _selectedAcademicMinistryId: string;
  availableAcademicMinistries: AcademicMinistryResponse[];
  @Input() private academicMinistryObservable: Observable<AcademicMinistryResponse[]>;
  private academicMinistriesSubscription: Subscription;
  @Output() academicMinistryIdSelected = new EventEmitter<string>();


  constructor() { }

  ngOnInit() {
    this.academicMinistriesSubscription = this.academicMinistryObservable
      .subscribe(academicMinistry => {
        this.availableAcademicMinistries = academicMinistry;
      });
  }

  get selectedAcademicMinistryId(): string {
    return this._selectedAcademicMinistryId;
  }

  set selectedAcademicMinistryId(value: string) {
    this._selectedAcademicMinistryId = value;
  }

}

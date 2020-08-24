import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AcademicMinistryResponse} from '../../../academic-ministry/service/rest/response/academic-ministry.response';
import {Observable, Subscription} from 'rxjs';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from '@angular/forms';

@Component({
  selector: 'bda-admin-academic-ministry-selection',
  templateUrl: './academic-ministry-selection.component.html',
  styleUrls: ['./academic-ministry-selection.component.less'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: AcademicMinistrySelectionComponent,
      multi: true
    }
  ]
})
export class AcademicMinistrySelectionComponent implements OnInit, ControlValueAccessor {

  private _selectedAcademicMinistryId: string;
  availableAcademicMinistries: AcademicMinistryResponse[];
  @Input() private academicMinistryObservable: Observable<AcademicMinistryResponse[]>;
  private academicMinistriesSubscription: Subscription;
  @Output() academicMinistryIdSelected = new EventEmitter<string>();
  @Input() disabled = false;

  private onChange;


  constructor() {
  }

  ngOnInit() {
    this.academicMinistriesSubscription = this.academicMinistryObservable
        .subscribe(academicMinistries => {
          this.availableAcademicMinistries = academicMinistries;
        });
  }

  get selectedAcademicMinistryId(): string {
    return this._selectedAcademicMinistryId;
  }

  set selectedAcademicMinistryId(value: string) {
    this._selectedAcademicMinistryId = value;
    this.academicMinistryIdSelected.emit(value);
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
  }

  setDisabledState(isDisabled: boolean): void {
  }

  writeValue(selectedAcademicMinistryId: string): void {
    this.selectedAcademicMinistryId = selectedAcademicMinistryId;
  }

}

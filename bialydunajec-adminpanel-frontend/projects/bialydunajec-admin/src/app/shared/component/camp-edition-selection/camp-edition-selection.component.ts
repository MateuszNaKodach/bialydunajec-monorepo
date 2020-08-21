import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {Observable, Subscription} from 'rxjs';
import {CampEditionResponse} from '../../../camp-edition/service/rest/response/camp-edition.response';
import {RomanNumerals} from '../../helper/RomanNumerals';

@Component({
  selector: 'bda-admin-camp-edition-selection',
  templateUrl: './camp-edition-selection.component.html',
  styleUrls: ['./camp-edition-selection.component.less']
})
export class CampEditionSelectionComponent implements OnInit, OnDestroy {
  RomanNumerals = RomanNumerals;
  private _selectedCampEditionId: number;
  availableCampEditions: CampEditionResponse[];
  @Input() private campEditionsObservable: Observable<CampEditionResponse[]>;
  private campEditionsSubscription: Subscription;
  @Output() campEditionIdSelected = new EventEmitter<number>();

  constructor() {
  }

  ngOnInit() {
    this.campEditionsSubscription = this.campEditionsObservable
      .subscribe(campEditions => {
        this.availableCampEditions = campEditions.sort((e1, e2) => e2.campEditionId - e1.campEditionId);
        this.selectedCampEditionId = this.availableCampEditions[0].campEditionId;
      });
  }

  ngOnDestroy() {
    this.campEditionsSubscription.unsubscribe();
  }

  get selectedCampEditionId(): number {
    return this._selectedCampEditionId;
  }

  set selectedCampEditionId(value: number) {
    this.campEditionIdSelected.emit(value);
    this._selectedCampEditionId = value;
  }

}

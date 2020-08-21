import {Component, Input, OnInit} from '@angular/core';
import {CottageCardViewModel} from '../cottage-card/cottage-card.view-model';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from '@angular/forms';
import {CampRegistrationsEndpoint} from '../../../../service/rest/camp-registrations-endpoint.service';
import {Observable} from 'rxjs';

@Component({
  selector: 'bda-cottage-selection',
  templateUrl: './cottage-selection.component.html',
  styleUrls: ['./cottage-selection.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: CottageSelectionComponent,
      multi: true
    }
  ]
})
export class CottageSelectionComponent implements OnInit, ControlValueAccessor {

  @Input() cottages: CottageCardViewModel[] = [
    {
      cottageId: '1',
      cottageName: 'Antoni',
      cottageLogoUrl: 'assets/images/logo/antoni.png',
      hasSpace: true
    },
    {
      cottageId: '2',
      cottageName: 'Dach',
      cottageLogoUrl: 'assets/images/logo/dach.png',
      hasSpace: false
    },
    {
      cottageId: '3',
      cottageName: 'Dominik',
      cottageLogoUrl: 'assets/images/logo/dominik.png',
      hasSpace: true
    },
    {
      cottageId: '8',
      cottageName: 'Horeb',
      cottageLogoUrl: 'assets/images/logo/horeb.png',
      hasSpace: false
    },
    {
      cottageId: '4',
      cottageName: 'Maciejówka',
      cottageLogoUrl: 'assets/images/logo/maciejowka.png',
      hasSpace: false
    },
    {
      cottageId: '5',
      cottageName: 'Redemptor',
      cottageLogoUrl: 'assets/images/logo/redemptor.png',
      hasSpace: true
    }
  ];


  private selectedCottage: CottageCardViewModel;
  private onChange;

  ngOnInit() {
  }

  onCottageClicked(cottage: CottageCardViewModel) {
    if (this.isSelectedCottage(cottage.cottageId)) {
      this.selectedCottage = null;
    } else if (cottage.hasSpace) {
      this.selectedCottage = cottage;
    } else if (!cottage.hasSpace) {
      console.log('Show modal with info about camp boss.');
    }
    this.onChange(this.selectedCottage === null ? null : this.selectedCottage.cottageId);
  }

  isSelectedCottage(cottageId: string) {
    return this.selectedCottage && this.selectedCottage.cottageId === cottageId;
  }


  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    // TODO: Implement disabled!
  }

  setDisabledState(isDisabled: boolean): void {
  }

  writeValue(cottageId: string): void {
    this.selectedCottage = this.cottages.find(c => c.cottageId === cottageId);
  }

}

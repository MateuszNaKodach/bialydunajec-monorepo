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
      cottageLogoUrl: 'http://bialydunajec.org:3344/api/v1/academic-ministry/3/logo',
      hasSpace: true
    },
    {
      cottageId: '2',
      cottageName: 'Dach',
      cottageLogoUrl: 'http://bialydunajec.org:3344/api/v1/academic-ministry/6/logo',
      hasSpace: false
    },
    {
      cottageId: '3',
      cottageName: 'Dominik',
      cottageLogoUrl: 'http://bialydunajec.org:3344/api/v1/academic-ministry/9/logo',
      hasSpace: true
    },
    {
      cottageId: '8',
      cottageName: 'Horeb',
      cottageLogoUrl: 'http://bialydunajec.org:3344/api/v1/academic-ministry/12/logo',
      hasSpace: false
    },
    {
      cottageId: '4',
      cottageName: 'Maciejówka',
      cottageLogoUrl: 'http://bialydunajec.org:3344/api/v1/academic-ministry/18/logo',
      hasSpace: false
    },
    {
      cottageId: '5',
      cottageName: 'Redemptor',
      cottageLogoUrl: 'http://bialydunajec.org:3344/api/v1/academic-ministry/21/logo',
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

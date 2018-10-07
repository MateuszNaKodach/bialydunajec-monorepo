import {Component, OnInit} from '@angular/core';
import {RomanNumerals} from '../../../shared/helper/RomanNumerals';
import {FormGroup, FormGroupDirective, NgForm} from '@angular/forms';
import {CampEditionEndpoint} from '../../service/rest/camp-edition.endpoint';
import {AngularFormHelper} from '../../../../../../bialydunajec-main/src/app/shared/helper/angular-form.helper';
import {CreateCampEditionRequest} from '../../service/rest/request/create-camp-edition.request';
import {finalize, tap} from 'rxjs/operators';
import {NzNotificationService} from 'ng-zorro-antd';

@Component({
  selector: 'bda-admin-camp-edition-edit',
  templateUrl: './camp-edition-edit.component.html',
  styleUrls: ['./camp-edition-edit.component.less']
})
export class CampEditionEditComponent implements OnInit {

  RomanNumerals = RomanNumerals;
  dateFormat = 'DD.MM.YYYY';
  submittingInProgress = false;


  constructor(
    private campEditionEndpoint: CampEditionEndpoint,
    private notificationService: NzNotificationService) {
  }

  ngOnInit() {
    this.notificationService.config({
      nzPlacement: 'topRight',
      nzDuration: 9000,
      nzTop: '6rem'
    });
  }

  onSubmit(form: FormGroup) {
    AngularFormHelper.markFormGroupDirty(form);
    const romanCampEditionNumber = form.value['campEditionRomanNumber'];
    const campEditionId = RomanNumerals.romanToNumber(romanCampEditionNumber);
    const campEditionStartDate = form.value['campEditionDuration'][0];
    const campEditionEndDate = form.value['campEditionDuration'][1];

    const createCampEditionRequest = new CreateCampEditionRequest(campEditionId, campEditionStartDate, campEditionEndDate);
    console.log(createCampEditionRequest);
    if (form.valid) {
      this.submittingInProgress = true;
      this.campEditionEndpoint.createCampEdition(createCampEditionRequest)
        .pipe(
          finalize(() => this.submittingInProgress = false)
        )
        .subscribe(
          _ => {
            form.reset();
            this.notificationService.create(
              'success',
              `${romanCampEditionNumber} Edycja Obozu`,
              'została poprawnie utworzona!'
            );
          },
          error => {
            console.log(error);
          }
        );
    }
  }

}

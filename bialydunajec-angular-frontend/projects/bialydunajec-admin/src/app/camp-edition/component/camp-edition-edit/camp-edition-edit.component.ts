import {Component, OnInit} from '@angular/core';
import {RomanNumerals} from '../../../shared/helper/RomanNumerals';
import {FormGroup, FormGroupDirective, NgForm} from '@angular/forms';
import {CampEditionEndpoint} from '../../service/rest/camp-edition.endpoint';
import {AngularFormHelper} from '../../../../../../bialydunajec-main/src/app/shared/helper/angular-form.helper';
import {CreateCampEditionRequest} from '../../service/rest/request/create-camp-edition.request';
import {finalize, tap} from 'rxjs/operators';
import {NzNotificationService} from 'ng-zorro-antd';
import {HttpErrorResponse} from '@angular/common/http';
import {HttpResponseHelper} from '../../../shared/helper/HttpResponseHelper';

@Component({
  selector: 'bda-admin-camp-edition-edit',
  templateUrl: './camp-edition-edit.component.html',
  styleUrls: ['./camp-edition-edit.component.less']
})
export class CampEditionEditComponent implements OnInit {

  RomanNumerals = RomanNumerals;
  dateFormat = 'DD.MM.YYYY';
  submittingInProgress = false;
  lastAlert: { type: string; message: string; description: string };


  constructor(
    private campEditionEndpoint: CampEditionEndpoint) {
  }

  ngOnInit() {
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
      this.lastAlert = null;
      this.submittingInProgress = true;
      this.campEditionEndpoint.createCampEdition(createCampEditionRequest)
        .pipe(
          finalize(() => this.submittingInProgress = false)
        )
        .subscribe(
          _ => {
            form.reset();
            this.lastAlert = {
              type: 'success',
              message: `${romanCampEditionNumber} Edycja Obozu`,
              description: 'została poprawnie utworzona!'
            };
          },
          (response: HttpErrorResponse) => {
            const error = response.error;
            const restErrors = response.error.restErrors;
            if (HttpResponseHelper.isStatus4xx(response) && restErrors) {
              this.lastAlert = {
                type: 'error',
                message: `${romanCampEditionNumber} Edycja Obozu`,
                description: 'nie została utworzona, z powodu błędów:' +
                  restErrors.map((e: string) => ` ${e}`)
              };
            } else {
              this.lastAlert = {
                type: 'error',
                message: `${romanCampEditionNumber} Edycja Obozu`,
                description: 'nie została utworzona, z powodu nieznanych błędów, skontaktuj się z administratorem.'
              };
            }
          }
        );
    }
  }

}

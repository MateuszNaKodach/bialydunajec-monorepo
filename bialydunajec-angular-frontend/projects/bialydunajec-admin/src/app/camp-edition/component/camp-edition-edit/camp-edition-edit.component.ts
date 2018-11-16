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
import {AuthService} from '../../../auth/service/auth.service';
import {Router} from '@angular/router';

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

  initialDownPaymentAmount = 99;
  initialPrice = 419;
  formatterPln = value => `${value} zł`;
  parserPln = value => value.replace(' zł', '');

  constructor(private campEditionEndpoint: CampEditionEndpoint, private router: Router) {
  }

  ngOnInit() {
  }

  onSubmit(form: FormGroup) {
    AngularFormHelper.markFormGroupDirty(form);
    const romanCampEditionNumber = form.value['campEditionRomanNumber'];
    const campEditionId = RomanNumerals.romanToNumber(romanCampEditionNumber);
    const campEditionStartDate = form.value['campEditionDuration'][0];
    const campEditionEndDate = form.value['campEditionDuration'][1];
    const campEditionPrice = form.value['campEditionPrice'];
    const campEditionDownPaymentAmount = form.value['downPaymentAmount'];

    const createCampEditionRequest =
      new CreateCampEditionRequest(campEditionId, campEditionStartDate, campEditionEndDate, campEditionPrice, campEditionDownPaymentAmount);
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
            this.router.navigate(['/panel/camp-editions']);
          },
          (response: HttpErrorResponse) => {
            console.log(response);
            const error = response.error;
            const restErrors = response.error.restErrors;
            if (HttpResponseHelper.isStatus4xx(response) && restErrors) {
              this.lastAlert = {
                type: 'error',
                message: `${romanCampEditionNumber} Edycja Obozu`,
                description: 'nie została utworzona, z powodu złamania reguł:' +
                  restErrors.map((e: string) => ` ${e}`)
              };
            } else if (response.status === 0) {
              this.lastAlert = {
                type: 'error',
                message: `${romanCampEditionNumber} Edycja Obozu`,
                description: 'nie została utworzona, z powodu braku odpowiedzi serwera.'
              };
            } else {
              this.lastAlert = {
                type: 'error',
                message: `${romanCampEditionNumber} Edycja Obozu`,
                description: `nie została utworzona, z powodu błędu (jeśli nie wiesz co zrobić, to skontaktuj się z administratorem): \n ${error.message}`
              };
            }
          }
        );
    }
  }

}

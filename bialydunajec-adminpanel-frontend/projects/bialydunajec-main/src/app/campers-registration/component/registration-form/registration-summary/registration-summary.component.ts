import {Component, OnInit} from '@angular/core';
import {
  CamperEducationDto,
  CamperPersonalDataDto,
  CampParticipantRegistrationRequest
} from '../../../service/rest/request/camp-participant-registration.request';
import {AddressDto} from '../../../../../../../bialydunajec-admin/src/app/shared/service/rest/dto/address.dto';
import {CamperRegistrationFormStateService} from '../../../service/camper-registration-form-state.service';
import {CampRegistrationsEndpoint} from '../../../service/rest/camp-registrations-endpoint.service';
import {finalize} from 'rxjs/operators';
import {ErrorObserver} from 'rxjs';
import {HttpErrorResponse} from '@angular/common/http';
import {HttpResponseHelper} from '../../../../../../../bialydunajec-admin/src/app/shared/helper/HttpResponseHelper';
import {RestErrorCode} from '../../../service/rest/response/rest-error.code';

@Component({
  selector: 'bda-registration-summary',
  templateUrl: './registration-summary.component.html',
  styleUrls: ['./registration-summary.component.scss']
})
export class RegistrationSummaryComponent implements OnInit {

  lastMessage: { additionalClass: string, icon: string, header: string, content: string, };

  submittingInProgress = false;
  registeredSuccessful = false;

  constructor(
    private mainFormState: CamperRegistrationFormStateService,
    private campRegistrationsEndpoint: CampRegistrationsEndpoint) {
  }

  ngOnInit() {
    this.registerCamper();
  }

  private registerCamper() {
    this.submittingInProgress = true;
    this.lastMessage = null;

    const formState = this.mainFormState.getFormDataSnapshot();
    const personalDataState = formState.PERSONAL_DATA;

    const camperPersonalDataDto = new CamperPersonalDataDto(
      personalDataState.personalData.firstName,
      personalDataState.personalData.lastName,
      personalDataState.personalData.gender,
      personalDataState.personalData.pesel
    );

    const camperEducationDto = new CamperEducationDto(
      personalDataState.education.university,
      personalDataState.education.faculty,
      personalDataState.education.fieldOfStudy,
      personalDataState.education.isRecentHighSchoolGraduate,
      personalDataState.education.highSchool,
    );

    const camperAddressDto = new AddressDto(
      personalDataState.homeAddress.street,
      personalDataState.homeAddress.number,
      personalDataState.homeAddress.city,
      personalDataState.homeAddress.postalCode
    );

    const request = new CampParticipantRegistrationRequest(
      formState.COTTAGE.cottageId,
      camperPersonalDataDto,
      camperAddressDto,
      personalDataState.contact.telephone,
      personalDataState.contact.email,
      camperEducationDto
    );

    this.campRegistrationsEndpoint.registerCampParticipant(36, request)
      .pipe(
        finalize(() => this.submittingInProgress = false)
      )
      .subscribe(
        r => {
          this.registeredSuccessful = true;
          this.lastMessage = {
            additionalClass: 'success',
            icon: 'check circle outline icon',
            header: 'Zapisaliśmy Ciebie na Obóz!',
            content: 'Sprawdź e-mail.'
          };
        },
        e => new RequestErrorObserverBuilder(
          (restErrors: RestErrorCode[]) => {
            if (restErrors.includes(RestErrorCode.CAMP_PARTICIPANT_WITH_GIVEN_PESEL_IS_ALREADY_REGISTERED)) {

            }
            console.log('REST ERROR:', restErrors);
            this.lastMessage = {
              additionalClass: 'negative',
              icon: 'times circle outline icon',
              header: 'Jesteś już zapisany na Obóz!',
              content: 'Wygląda na to, że zapisałeś się już wcześniej. Jeśli tego nie zrobiłeś, skontaktuj się z administratorem.'
            };
          }
        )
      );
  }


}

export class RequestErrorObserverBuilder {
  restError: (restErrors: string[] | RestErrorCode[]) => any;
  networkError: (error) => any;
  unhandledError: (error) => any;

  private static defaultCallback = error => console.log(error);

  constructor(
    restError: (restErrors: string[] | RestErrorCode[]) => any = RequestErrorObserverBuilder.defaultCallback,
    networkError: (error) => any = RequestErrorObserverBuilder.defaultCallback,
    unhandledError: (error) => any = RequestErrorObserverBuilder.defaultCallback) {
    this.restError = restError;
    this.networkError = networkError;
    this.unhandledError = unhandledError;
  }

  getRequestErrorObserver() {
    return (response: HttpErrorResponse) => {
      const error = response.error;
      const restErrors = response.error.restErrors;
      if (HttpResponseHelper.isStatus4xx(response) && restErrors) {
        this.restError(restErrors);
      } else if (response.status === 0) {
        this.networkError(error);
      } else {
        this.unhandledError(error);
      }
    };
  }
}





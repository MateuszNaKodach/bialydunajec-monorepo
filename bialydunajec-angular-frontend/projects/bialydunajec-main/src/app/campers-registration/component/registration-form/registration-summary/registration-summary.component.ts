import {Component, OnInit} from '@angular/core';
import {
  CamperEducationDto,
  CamperPersonalDataDto,
  CamperShirtOrderDto,
  CampParticipantRegistrationRequest,
  StatisticalAnswersDto
} from '../../../service/rest/request/camp-participant-registration.request';
import {AddressDto} from '../../../../shared/dto/address.dto';
import {CamperRegistrationFormStateService} from '../../../service/camper-registration-form-state.service';
import {CampRegistrationsEndpoint} from '../../../service/rest/camp-registrations-endpoint.service';
import {finalize} from 'rxjs/operators';
import {RestErrorCode} from '../../../service/rest/response/rest-error.code';
import {RequestErrorObserverBuilder} from '../../../../shared/helper/request-error-observer.builder';

@Component({
  selector: 'bda-registration-summary',
  templateUrl: './registration-summary.component.html',
  styleUrls: ['./registration-summary.component.scss']
})
export class RegistrationSummaryComponent implements OnInit {

  lastMessage: { additionalClass: string, icon: string, header: string, content: string, };

  submittingInProgress = false;
  registeredSuccessful = false;

  showPreviousButton = false;
  showResendVerificationEmail = false;

  firstName: string;

  constructor(
    private mainFormState: CamperRegistrationFormStateService,
    private campRegistrationsEndpoint: CampRegistrationsEndpoint) {
  }

  ngOnInit() {
    this.registerCamper();
    this.firstName = this.mainFormState.getPersonalFormDataSnapshot().personalData.firstName;
  }

  private registerCamper() {
    this.submittingInProgress = true;
    this.lastMessage = null;

    const formState = this.mainFormState.getFormDataSnapshot();
    const personalDataState = formState.PERSONAL_DATA;
    const shirtOrderState = formState.SHIRT;


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
      personalDataState.education.highSchool,
      personalDataState.education.isRecentHighSchoolGraduate
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
      camperEducationDto,
      new CamperShirtOrderDto(
        shirtOrderState.color.shirtColorOptionId,
        shirtOrderState.size.shirtSizeOptionId
      ),
      new StatisticalAnswersDto(
        personalDataState.statistics.knowAboutCampFrom,
        personalDataState.statistics.onCampForTime
      ),
      formState.TRANSPORT.meanOfTransport
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
            content: 'Właśnie na Twojego maila wysłaliśmy wiadomość (prosimy, sprawdź czy dojdzie w ciągu 5 minut). Prosimy o szczegółowe zapoznanie się z jej treścią, a także o kliknięcie w ciągu trzech dni w przesłany link potwierdzający, Twój zapis na Obóz. Jeśli tego nie zrobisz, to po tym czasie Twoje miejsce zwolnimy komuś innemu.'
          };
          this.mainFormState.resetFormState();
        },
        new RequestErrorObserverBuilder(
          (restErrors: string[]) => {
            if (restErrors.includes(RestErrorCode.CAMP_PARTICIPANT_WITH_GIVEN_PESEL_IS_ALREADY_REGISTERED)) {
              this.lastMessage = {
                additionalClass: 'negative',
                icon: 'times circle outline icon',
                header: 'Jesteś już zapisany/-a na Obóz!',
                content: 'Wygląda na to, że zapisałeś/-aś się już wcześniej. Jeśli tego nie zrobiłeś/-aś, skontaktuj się z administratorem.'
              };
              this.showResendVerificationEmail = true;
            } else if (restErrors.includes(RestErrorCode.COTTAGE_NOT_FOUND)) {
              this.lastMessage = {
                additionalClass: 'negative',
                icon: 'times circle outline icon',
                header: 'Brak miejsca w wybranej chatce!',
                content: 'Wygląda na to, że w międzyczasie ktoś Cię wyprzedził :( Jeśli bardzo chcesz zapisać się do tej chatki, spytaj jej szefa o taką możliwość.'
              };
              this.showPreviousButton = true;
            } else if (restErrors.includes(RestErrorCode.CAMP_EDITION_HAS_NOT_IN_PROGRESS_REGISTRATIONS)) {
              this.lastMessage = {
                additionalClass: 'negative',
                icon: 'times circle outline icon',
                header: 'Zapisy na Obóz są nieaktywne!',
                content: 'Niestety zapisy na Obóz są teraz nieaktywne :( Spróbuj ponownie później.'
              };
            }
          },
          unhandledError => {
            this.lastMessage = {
              additionalClass: 'negative',
              icon: 'times circle outline icon',
              header: 'Błąd!',
              content: 'Niestety...'
            };
          },
          networkError => {
            this.lastMessage = {
              additionalClass: 'negative',
              icon: 'times circle outline icon',
              header: 'Błąd!',
              content: 'Niestety...'
            };
          }
        ).getRequestErrorObserver()
      );
  }


}

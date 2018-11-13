import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {CampRegistrationsEndpoint} from '../../service/rest/camp-registrations-endpoint.service';
import {finalize} from 'rxjs/operators';
import {HttpResponseHelper} from '../../../../../../bialydunajec-admin/src/app/shared/helper/HttpResponseHelper';

@Component({
  selector: 'bda-registration-verification',
  templateUrl: './registration-verification.component.html',
  styleUrls: ['./registration-verification.component.scss']
})
export class RegistrationVerificationComponent implements OnInit {

  lastMessage: { additionalClass: string, success: boolean, icon: string, header: string, content: string, } = {
    success: undefined,
    additionalClass: '',
    icon: 'notched circle loading icon',
    header: 'Trwa weryfikacja Twojego zgłoszenia',
    content: 'Sprawdzamy tylko czy wszystko jest w porządku...'
  };

  submittingVerification;

  constructor(
    private route: ActivatedRoute,
    private campRegistrationsEndpoint: CampRegistrationsEndpoint
  ) {
  }

  ngOnInit() {
    const campParticipantRegistrationId = this.route.snapshot.queryParamMap.get('zgloszenie');
    const verificationCode = this.route.snapshot.queryParamMap.get('kod-weryfikacyjny');

    if (verificationCode && campParticipantRegistrationId) {
      this.submitVerification(campParticipantRegistrationId, verificationCode);
    } else {
      this.lastMessage = {
        success: false,
        additionalClass: 'negative',
        icon: 'times circle outline icon',
        header: 'Nie znaleziono zgłoszenia',
        content: 'Podałeś zły numer zgłoszenia, nie byliśmy w stanie go potwierdzić :( Jeżeli jesteś pewien poprawności skontaktuj się z administratorem zapisów.'
      };
    }

  }

  private submitVerification(campParticipantRegistrationId, verificationCode) {
    this.submittingVerification = true;
    this.campRegistrationsEndpoint.verifyCampParticipantRegistration(campParticipantRegistrationId, {verificationCode})
      .pipe(
        finalize(() => this.submittingVerification = false)
      )
      .subscribe(
        () => {
          this.lastMessage = {
            success: true,
            additionalClass: 'success',
            icon: 'check circle outline icon',
            header: 'Zgłoszenie zostało potwierdzone',
            content: 'Na Twój adres email wysłaliśmy wszystkie potrzebne informacje. Prosimy przeczytać je bardzo dokładnie. Do zobaczenia na Obozie!'
          };
        },
        (response) => {
          console.log(response);
          const error = response.error;
          const restErrors = response.error.restErrors;
          if (HttpResponseHelper.isStatus4xx(response) && restErrors) {
            console.log(restErrors);
            if (restErrors.indexOf('CAMP_PARTICIPANT_REGISTRATION_ALREADY_VERIFIED') !== -1) {
              this.lastMessage = {
                success: true,
                additionalClass: 'success',
                icon: 'check circle outline icon',
                header: 'Już wcześniej potwierdziłeś zgłoszenie',
                content: 'Na Twój adres email wysłaliśmy wszystkie potrzebne informacje. Prosimy przeczytać je bardzo dokładnie. Do zobaczenia na Obozie!'
              };
            } else if (restErrors.indexOf('CAMP_PARTICIPANT_REGISTRATION_ALREADY_VERIFIED_BY_AUTHORIZED') !== -1) {
              this.lastMessage = {
                success: true,
                additionalClass: 'success',
                icon: 'check circle outline icon',
                header: 'Twoje zgłoszenie zostało już wcześniej potweridzone przez administratora',
                content: 'Na Twój adres email wysłaliśmy wszystkie potrzebne informacje. Prosimy przeczytać je bardzo dokładnie. Do zobaczenia na Obozie!'
              };
            } else if (restErrors.indexOf('INVALID_CAMP_PARTICIPANT_REGISTRATION_VERIFICATION_CODE') !== -1) {
              this.lastMessage = {
                success: false,
                additionalClass: 'negative',
                icon: 'times circle outline icon',
                header: 'Niepoprawny kod weryfikacjny',
                content: 'Podałeś zły kod do weryfikacji. Upewnić się, że na pewno skopiowałeś cały adres z wiadomości e-mail! Jeżeli jesteś pewien poprawności skontaktuj się z administratorem zapisów.'
              };
            } else if (restErrors.indexOf('CAMP_PARTICIPANT_REGISTRATIONS_TO_CONFIRM_MUST_EXISTS') !== -1) {
              this.lastMessage = {
                success: false,
                additionalClass: 'negative',
                icon: 'times circle outline icon',
                header: 'Nie znaleziono zgłoszenia',
                content: 'Podałeś zły numer zgłoszenia, nie byliśmy w stanie go potwierdzić :( Jeżeli jesteś pewien poprawności skontaktuj się z administratorem zapisów.'
              };
            } else if (
              restErrors.indexOf('CAMP_PARTICIPANT_REGISTRATION_ALREADY_CANCELLED') !== -1
              || restErrors.indexOf('CAMP_PARTICIPANT_REGISTRATION_ALREADY_CANCELLED_BY_AUTHORIZED') !== -1
            ) {
              this.lastMessage = {
                success: false,
                additionalClass: 'negative',
                icon: 'times circle outline icon',
                header: 'Twoje zgłoszenie zostało anulowane',
                content: 'Jeśli nie wiesz co jest tego powodem, to skontaktuj się z administratorem systemu zapisów.'
              };
            } else if (restErrors.indexOf('CAMP_PARTICIPANT_REGISTRATION_ALREADY_CANCELLED_BY_DEADLINE') !== -1) {
              this.lastMessage = {
                success: false,
                additionalClass: 'negative',
                icon: 'times circle outline icon',
                header: 'Twoje zgłoszenie zostało anulowane',
                content: 'Czas na potwierdzenie Twojego złoszenia minął. Jeśli chcesz pojechać na Obóz zapisz się ponownie.'
              };
            }
          } else if (response.status === 0) {
            this.lastMessage = {
              success: false,
              additionalClass: 'negative',
              icon: 'times circle outline icon',
              header: 'Brak odpowiedzi serwera',
              content: 'Serwer aplikacji jest niedostępny, spróbuj ponownie później'
            };
          } else {
            this.lastMessage = {
              success: false,
              additionalClass: 'negative',
              icon: 'times circle outline icon',
              header: 'Wystąpił nieznany błąd',
              content: 'Twoje zgłoszenie mogło nie zostać potwierdzone. Dla pewności skontaktuj się z administratorem.'
            };
          }
        }
      );
  }

}

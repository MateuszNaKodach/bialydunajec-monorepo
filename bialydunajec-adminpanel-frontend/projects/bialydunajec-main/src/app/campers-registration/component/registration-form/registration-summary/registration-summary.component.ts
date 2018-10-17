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

@Component({
  selector: 'bda-registration-summary',
  templateUrl: './registration-summary.component.html',
  styleUrls: ['./registration-summary.component.scss']
})
export class RegistrationSummaryComponent implements OnInit {

  submittingInProgress = false;

  constructor(
    private mainFormState: CamperRegistrationFormStateService,
    private campRegistrationsEndpoint: CampRegistrationsEndpoint) {
  }

  ngOnInit() {
    this.registerCamper();
  }

  private registerCamper() {
    this.submittingInProgress = true;

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
        r => console.log(r),
        e => console.log(e)
      );
  }

}

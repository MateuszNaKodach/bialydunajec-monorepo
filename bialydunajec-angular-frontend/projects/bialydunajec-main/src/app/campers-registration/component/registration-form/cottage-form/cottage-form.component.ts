import {Component} from '@angular/core';
import {RegistrationFormStepAbstractComponent} from '../registration-form-step.abstract-component';
import {CamperRegistrationFormStateService} from '../../../service/camper-registration-form-state.service';
import {CamperRegistrationFormNavigator} from '../../../service/camper-registration-form.navigator';
import {ActivatedRoute} from '@angular/router';
import {FormBuilder, Validators} from '@angular/forms';
import {StepId} from '../registration-form.config';
import {InProgressCampRegistrationsEndpoint} from '../../../service/rest/in-progress-camp-registrations.endpoint';
import {
  CamperEducationDto,
  CamperPersonalDataDto,
  CampParticipantRegistrationRequest
} from '../../../service/rest/request/camp-participant-registration.request';
import {AddressDto} from '../../../../../../../bialydunajec-admin/src/app/shared/service/rest/dto/address.dto';
import {CottageCardViewModel} from './cottage-card/cottage-card.view-model';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {CampRegistrationsCottageResponse} from '../../../service/rest/response/camp-registrations-cottage.response';

@Component({
  selector: 'bda-cottage-form',
  templateUrl: './cottage-form.component.html',
  styleUrls: ['./cottage-form.component.scss']
})
export class CottageFormComponent extends RegistrationFormStepAbstractComponent {

  cottages: Observable<CottageCardViewModel[]>;

  constructor(
    mainFormState: CamperRegistrationFormStateService,
    formNavigator: CamperRegistrationFormNavigator,
    stepRoute: ActivatedRoute,
    private formBuilder: FormBuilder,
    private inProgressCampRegistrationsEndpoint: InProgressCampRegistrationsEndpoint) {
    super(StepId.COTTAGE, mainFormState, formNavigator, stepRoute);
  }

  protected initStepFormControls() {
    this.stepForm = this.formBuilder.group({
        cottageId: [null, [Validators.required]]
      }
    );
  }

  startFormStepInit() {
    const camperGender = this.mainFormState.getPersonalFormDataSnapshot().personalData.gender;
    this.cottages = this.inProgressCampRegistrationsEndpoint.getAllCottagesByInProgressCampRegistrations(camperGender)
      .pipe(
        map(
          (response: CampRegistrationsCottageResponse[]) =>
            response.map(cottage => new CottageCardViewModel(
              cottage.cottageId, cottage.name, cottage.logoImageUrl, cottage.hasSpace
            ))
        )
      );
  }

  get cottageId() {
    return this.stepForm.get('cottageId');
  }

  onClickCampSignUp() {
    this.onSubmitStepForm();
    if (this.mainFormState.isFormValid()) {
      this.registerCamper();
    } else {
      console.log('FORM INVALID!');
    }
  }

  private registerCamper() {
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

    this.inProgressCampRegistrationsEndpoint.registerCampParticipant(36, request)
      .subscribe(
        r => console.log(r),
        e => console.log(e)
      );
  }
}

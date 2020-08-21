import {Component} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {peselValidator} from '../../../../shared/validator/pesel.validator';
import {CamperRegistrationFormNavigator} from '../../../service/camper-registration-form.navigator';
import {ActivatedRoute} from '@angular/router';
import {CamperRegistrationFormStateService} from '../../../service/camper-registration-form-state.service';
import {StepId} from '../registration-form.config';
import {RegistrationFormStepAbstractComponent} from '../registration-form-step.abstract-component';


@Component({
  selector: 'bda-personal-data-form',
  templateUrl: './personal-data-form.component.html',
  styleUrls: ['./personal-data-form.component.scss']
})
export class PersonalDataFormComponent extends RegistrationFormStepAbstractComponent {

  regulation = `Akceptuję <b><a target="_blank" rel="noopener noreferrer" href="assets/documents/REGULAMIN_BD_DLA_UCZESTNIKA.pdf">regulamin</a></b> Obozu i zobowiązuję się do wypełniania jego postanowień.`;

  campInfoOptions = [
    'od znajomych',
    'z duszpasterstwa',
    'ze szkoły',
    'z uczelni',
    'z facebooka',
    'podczas pielgrzymki do Częstochowy',
    'z kościoła',
    'z gazety',
    'z innego źródła'
  ];
  whichOneGoForCamp = ['1', '2', '3', '4', '5', '6', '7'];

  constructor(
    private formBuilder: FormBuilder,
    mainFormState: CamperRegistrationFormStateService,
    formNavigator: CamperRegistrationFormNavigator,
    stepRoute: ActivatedRoute) {
    super(StepId.PERSONAL_DATA, mainFormState, formNavigator, stepRoute);
  }

  protected initStepFormControls() {
    this.stepForm = this.formBuilder.group(
      {
        personalData: this.formBuilder.group(
          {
            gender: [null, [Validators.required]],
            firstName: [null, [Validators.required]],
            lastName: [null, [Validators.required]],
            pesel: [null, [Validators.required, peselValidator]]
          }
        ),
        homeAddress: this.formBuilder.group(
          {
            street: [null, [Validators.required]],
            number: [null, [Validators.required]],
            postalCode: [null, [Validators.required]],
            city: [null, [Validators.required]]
          }
        ),
        contact: this.formBuilder.group(
          {
            email: [null, [Validators.required, Validators.email]],
            telephone: [null, [Validators.required]] //TODO: Add phone number validator
          }
        ),
        education: this.formBuilder.group(
          {
            university: [null, [Validators.required]],
            faculty: [null, [Validators.required]],
            fieldOfStudy: [null, [Validators.required]],
            isRecentHighSchoolGraduate: [null, [Validators.required]],
            highSchool: [null, []]
          }
        ),
        statistics: this.formBuilder.group(
          {
            knowAboutCampFrom: [null, [Validators.required]],
            onCampForTime: [null, [Validators.required]]
          }
        ),
        agreements: this.formBuilder.group(
          {
            campRegulations: [null, [Validators.requiredTrue]],
            camperOwnResponsibility: [null, [Validators.requiredTrue]],
            personalDataProcessing: [null, [Validators.requiredTrue]]
          }
        )
      }
    );

    this.stepForm.get(['education', 'isRecentHighSchoolGraduate']).valueChanges
      .subscribe(value => this.onIsRecentHighSchoolGraduateValueChanged(value));
  }


  onIsRecentHighSchoolGraduateValueChanged(isRecentHighSchoolGraduate: boolean) {
    const highSchoolControl = this.stepForm.get(['education', 'highSchool']);
    if (isRecentHighSchoolGraduate) {
      highSchoolControl.setValidators(Validators.required);
    } else {
      highSchoolControl.clearValidators();
    }
    highSchoolControl.updateValueAndValidity();
  }

  get gender() {
    return this.getPersonalDataControl('gender');
  }

  get firstName() {
    return this.getPersonalDataControl('firstName');
  }

  get lastName() {
    return this.getPersonalDataControl('lastName');
  }

  get pesel() {
    return this.getPersonalDataControl('pesel');
  }

  get emailAddress() {
    return this.getContactControl('email');
  }

  get phoneNumber() {
    return this.getContactControl('telephone');
  }

  get street() {
    return this.getHomeAddressControl('street');
  }

  get number() {
    return this.getHomeAddressControl('number');
  }

  get postalCode() {
    return this.getHomeAddressControl('postalCode');
  }

  get city() {
    return this.getHomeAddressControl('city');
  }

  get university() {
    return this.getEducationControl('university');
  }

  get faculty() {
    return this.getEducationControl('faculty');
  }

  get fieldOfStudy() {
    return this.getEducationControl('fieldOfStudy');
  }

  get isRecentHighSchoolGraduate() {
    return this.getEducationControl('isRecentHighSchoolGraduate');
  }

  get highSchool() {
    return this.getEducationControl('highSchool');
  }

  get knowAboutCampFrom() {
    return this.getStatisticsControl('knowAboutCampFrom');
  }

  get onCampForTime() {
    return this.getStatisticsControl('onCampForTime');
  }

  get campRegulations() {
    return this.getAgreementsControl('campRegulations');
  }

  get camperOwnResponsibility() {
    return this.getAgreementsControl('camperOwnResponsibility');
  }

  get personalDataProcessing() {
    return this.getAgreementsControl('personalDataProcessing');
  }

  private getPersonalDataControl(name: string) {
    return this.stepForm.get(['personalData', name]);
  }

  private getContactControl(name: string) {
    return this.stepForm.get(['contact', name]);
  }

  private getHomeAddressControl(name: string) {
    return this.stepForm.get(['homeAddress', name]);
  }

  private getEducationControl(name: string) {
    return this.stepForm.get(['education', name]);
  }

  private getStatisticsControl(name: string) {
    return this.stepForm.get(['statistics', name]);
  }

  private getAgreementsControl(name: string) {
    return this.stepForm.get(['agreements', name]);
  }

}

import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {peselValidator} from '../../../../shared/validator/pesel.validator';
import {CamperRegistrationFormNavigator} from '../../../service/camper-registration-form.navigator';
import {ActivatedRoute} from '@angular/router';
import {CamperRegistrationFormStateService} from '../../../service/camper-registration-form-state.service';
import {StepId} from '../registration-form.config';
import {FormStatus} from '../../../model/form-status.enum';


@Component({
  selector: 'bda-personal-data-form',
  templateUrl: './personal-data-form.component.html',
  styleUrls: ['./personal-data-form.component.scss']
})
export class PersonalDataFormComponent implements OnInit {


  personalDataForm: FormGroup;
  campInfoOptions = ['Ze szkoły', 'Z uczelni', 'Od znajomych', 'Z facebooka'];
  whichOneGoForCamp = ['drugi', 'trzeci', 'czwarty', 'piąty', 'szósty', 'siódmy', 'ósmy'];

  constructor(
    private formBuilder: FormBuilder,
    private formState: CamperRegistrationFormStateService,
    private formNavigator: CamperRegistrationFormNavigator,
    private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.initForm();
  }

  private initForm() {
    const initialFormState = this.formState.getPersonalFormDataSnapshot();
    const currentPersonalData = initialFormState.personalData;
    const currentHomeAddress = initialFormState.homeAddress;
    const currentContact = initialFormState.contact;
    const currentEducation = initialFormState.education;


    this.personalDataForm = this.formBuilder.group(
      {
        personalData: this.formBuilder.group(
          {
            gender: new FormControl(currentPersonalData.gender, [Validators.required]),
            firstName: new FormControl(currentPersonalData.firstName, [Validators.required]),
            lastName: new FormControl(currentPersonalData.lastName, [Validators.required]),
            pesel: new FormControl(currentPersonalData.pesel, [peselValidator])
          }
        ),
        homeAddress: this.formBuilder.group(
          {
            street: new FormControl(currentHomeAddress.street, [Validators.required]),
            number: new FormControl(currentHomeAddress.number, [Validators.required]),
            postalCode: new FormControl(currentHomeAddress.postalCode, [Validators.required]),
            city: new FormControl(currentHomeAddress.city, [Validators.required])
          }
        ),
        contact: this.formBuilder.group(
          {
            email: new FormControl(currentContact.email, [Validators.required, Validators.email]),
            telephone: new FormControl(currentContact.telephone, [Validators.required])
          }
        ),
        education: this.formBuilder.group(
          {
            university: new FormControl(currentEducation.university, [Validators.required]),
            faculty: new FormControl(currentEducation.faculty, [Validators.required]),
            fieldOfStudy: new FormControl(currentEducation.fieldOfStudy, [Validators.required]),
            isRecentHighSchoolGraduate: new FormControl(currentEducation.isRecentHighSchoolGraduate, [Validators.required]),
            highSchool: new FormControl(currentEducation.highSchool, [])
          }
        ),
        statistics: this.formBuilder.group(
          {
            knowAboutCampFrom: new FormControl(),
            wasCamperInThePast: new FormControl(),
            onCampForTime: new FormControl()
          }
        )
      }
    );

    this.personalDataForm.get(['education', 'isRecentHighSchoolGraduate']).valueChanges
      .subscribe(value => this.onIsRecentHighSchoolGraduateValueChanged(value));

  }

  onIsRecentHighSchoolGraduateValueChanged(isRecentHighSchoolGraduate: boolean) {
    const highSchoolControl = this.personalDataForm.get(['education', 'highSchool']);
    if (isRecentHighSchoolGraduate) {
      highSchoolControl.setValidators(Validators.required);
    } else {
      highSchoolControl.clearValidators();
    }
    highSchoolControl.updateValueAndValidity();
  }

  onSubmit() {
    const formValues = this.personalDataForm.value;
    console.log('Submit form state');
    this.formState.savePersonalFormData(formValues);
    this.formState.updateFormStatus(StepId.PERSONAL_DATA, FormStatus[this.personalDataForm.status]);
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

  private getPersonalDataControl(name: string) {
    return this.personalDataForm.get(['personalData', name]);
  }

  onClickNext() {
    this.formNavigator.navigateToNextStep(this.activatedRoute);
  }

}

import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {peselValidator} from '../../../../shared/validator/pesel.validator';


@Component({
  selector: 'bda-personal-data-form',
  templateUrl: './personal-data-form.component.html',
  styleUrls: ['./personal-data-form.component.scss']
})
export class PersonalDataFormComponent implements OnInit {


  personalDataForm: FormGroup;
  campInfoOptions = ['Ze szkoły', 'Z uczelni', 'Od znajomych', 'Z facebooka'];
  whichOneGoForCamp = ['drugi', 'trzeci', 'czwarty', 'piąty', 'szósty', 'siódmy', 'ósmy'];

  constructor(private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.initForm();
  }

  private initForm() {
    const currentFormState = {
      personalData: {
        gender: null,
        firstName: null,
        lastName: 'Kowalski',
        pesel: null
      },
      homeAddress: {
        street: null,
        number: null,
        postalCode: null,
        city: null
      },
      contact: {
        email: null,
        telephone: null,
      },
      education: {
        university: null,
        faculty: null,
        fieldOfStudy: null,
        isRecentHighSchoolGraduate: false,
        highSchool: null
      },
      statistics: {
        wasCamperInThePast: null
      }
    };
    const currentPersonalData = currentFormState.personalData;
    const currentHomeAddress = currentFormState.homeAddress;
    const currentContact = currentFormState.contact;
    const currentEducation = currentFormState.education;


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
    console.log(formValues);
    console.log(this.personalDataForm);
    console.log(this.personalDataForm.get(['education', 'isRecentHighSchoolGraduate']).value);
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

}

import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';


@Component({
  selector: 'bda-personal-data-form',
  templateUrl: './personal-data-form.component.html',
  styleUrls: ['./personal-data-form.component.scss']
})
export class PersonalDataFormComponent implements OnInit {


  personalDataForm: FormGroup;

  constructor() {
  }

  ngOnInit() {
    this.initForm();
  }

  private initForm() {
    this.personalDataForm = new FormGroup(
      {
        'personalData': new FormGroup(
          {
            'firstName': new FormControl(),
            'lastName': new FormControl(),
            'pesel': new FormControl()
          }
        ),
        'homeAddress': new FormGroup(
          {
            'street': new FormControl(),
            'number': new FormControl(),
            'postalCode': new FormControl(),
            'city': new FormControl()
          }
        ),
        'contact': new FormGroup(
          {
            'email': new FormControl(),
            'telephone': new FormControl()
          }
        ),
        'education': new FormGroup(
          {
            'university': new FormControl(),
            'faculty': new FormControl(),
            'fieldOfStudy': new FormControl(),
            'isRecentHighSchoolGraduate': new FormControl(),
            'highSchool': new FormControl()
          }
        ),
        'statistics': new FormGroup(
          {
            'wasCamperInThePast': new FormControl()
          }
        )
      }
    );
  }

  onSubmit() {
    const formValues = this.personalDataForm.value;
    console.log(formValues);
    console.log(this.personalDataForm);
  }

}

import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'bda-admin-academic-ministry-edit',
  templateUrl: './academic-ministry-edit.component.html',
  styleUrls: ['./academic-ministry-edit.component.less']
})
export class AcademicMinistryEditComponent implements OnInit {

  academicMinistryForm: FormGroup;

  constructor(private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.initForm();
  }

  initForm() {
    this.academicMinistryForm = this.formBuilder.group({
      officialName: [null, []],
      shortName: [null, [Validators.required]],
      logoImageUrl: [null, []],
      place: this.formBuilder.group({
        address: this.formBuilder.group({
          street: [null, []],
          homeNumber: [null, []],
          city: [null, []],
          postalCode: [null, []],
        }),
        geoLocation: this.formBuilder.group({
          latitude: [null, []],
          longitude: [null, []]
        }),
      }),
      socialMedia: this.formBuilder.group({
        webPageUrl: [null, []],
        facebookPageUrl: [null, []],
        facebookGroupUrl: [null, []],
        instagramUrl: [null, []],
        youTubeChannelUrl: [null, []],
      }),
      emailAddress: [null, []],
      photoUrl: [null, []],
      description: this.formBuilder.group({
        title: [],
        content: []
      })
    });
  }

}

import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';

@Component({
  selector: 'bda-transport-form',
  templateUrl: './transport-form.component.html',
  styleUrls: ['./transport-form.component.scss']
})
export class TransportFormComponent implements OnInit {

  form;
  testForm: FormGroup;

  constructor() {
  }

  ngOnInit() {
    this.testForm = new FormGroup({
      testControl: new FormControl()
    });







    this.form = {
      title: 'Dojazd',
      description: 'Poinformuj nas jak dojedziesz do Białego Dunajca :) Więcej informacji na temat naszego autokaru znajdziesz tutaj.',
      controls: [
        {
          label: 'Wybierz formę dojazdu',
          name: 'transportForm',
          type: 'select',
          placeholder: 'Wybierz formę dojazdu',
          options: [
            {id: '1', value: 'Samochodem'},
            {id: '2', value: 'Pociągiem / autobusem'},
            {id: '3', value: 'Rowerem'},
            {id: '4', value: 'Na stopa'},
            {id: '5', value: 'Autokarem Białego Dunajca (+55 zł)'}
          ]
        }
      ]
    };
  }

}

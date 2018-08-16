import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'bda-camp-sign-up-button',
  templateUrl: './camp-sign-up-button.component.html',
  styleUrls: ['./camp-sign-up-button.component.scss']
})
export class CampSignUpButtonComponent implements OnInit {

  @Input() style: string;
  @Input() text = 'ZAPISZ SIĘ!';

  constructor() {
  }

  ngOnInit() {
  }

}

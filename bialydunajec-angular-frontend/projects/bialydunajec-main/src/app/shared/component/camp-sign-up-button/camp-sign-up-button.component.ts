import {Component, Input, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {campersRegistrationRoutingPaths} from '../../../campers-registration/campers-registration-routing.paths';
import {appRoutingPaths} from '../../../app-routing.paths';

@Component({
  selector: 'bda-camp-sign-up-button',
  templateUrl: './camp-sign-up-button.component.html',
  styleUrls: ['./camp-sign-up-button.component.scss']
})
export class CampSignUpButtonComponent implements OnInit {

  @Input() style: string;
  @Input() text = 'ZAPISZ SIĘ!';

  constructor(private router: Router) {
  }

  ngOnInit() {
  }

  onClick() {
    this.router.navigate([appRoutingPaths.campersRegistration]);
  }

}

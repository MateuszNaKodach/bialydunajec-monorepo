import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {campersRegistrationRoutingPaths} from '../../../campers-registration/campers-registration-routing.paths';
import {appRoutingPaths} from '../../../app-routing.paths';
import {academicMinistriesCottagesRoutingPaths} from '../../../academic-ministries-cottages/academic-ministries-cottages-routing.paths';
import {tap} from 'rxjs/operators';

@Component({
  selector: 'bda-camp-sign-up-button',
  templateUrl: './camp-sign-up-button.component.html',
  styleUrls: ['./camp-sign-up-button.component.scss']
})
export class CampSignUpButtonComponent implements OnInit {

  @Input() style: string;
  @Input() text = 'ZAPISZ SIĘ!';

  constructor(private router: Router, private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
  }

  onClick() {
    this.router.navigate([appRoutingPaths.campersRegistration]);
  }

}

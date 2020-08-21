import {Component, Input, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {AlertViewModel} from '../../view-model/ng-zorro/alert.view-model';

@Component({
  selector: 'bda-admin-http-response-alter',
  templateUrl: './http-response-alter.component.html',
  styleUrls: ['./http-response-alter.component.less']
})
export class HttpResponseAlterComponent implements OnInit {

  @Input() alter: AlertViewModel;

  constructor() {
  }

  ngOnInit() {
  }

}

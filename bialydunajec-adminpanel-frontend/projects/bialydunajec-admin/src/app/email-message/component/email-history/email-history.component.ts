import {Component, OnInit} from '@angular/core';
import {EmailMessageEndpoint} from '../../service/rest/email-message.endpoint';
import {EmailMessageReadModel} from '../../service/rest/read-model/email-message.read-model';
import {Observable} from 'rxjs';
import {EmailStatisticsReadModel} from '../../service/rest/read-model/email-statistics.read-model';

@Component({
  selector: 'bda-admin-email-history',
  templateUrl: './email-history.component.html',
  styleUrls: ['./email-history.component.less']
})
export class EmailHistoryComponent implements OnInit {

  emailMessages$: Observable<EmailMessageReadModel[]>;
  emailStatistics$: Observable<EmailStatisticsReadModel>;


  constructor(private emailMessageEndpoint: EmailMessageEndpoint) {
  }

  ngOnInit() {
    this.emailMessages$ = this.emailMessageEndpoint.getAllEmailMessage();
    this.emailStatistics$ = this.emailMessageEndpoint.getEmailMessagesStatistics();
  }

}

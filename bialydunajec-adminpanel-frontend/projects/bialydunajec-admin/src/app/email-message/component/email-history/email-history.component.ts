import {Component, NgZone, OnInit} from '@angular/core';
import {EmailMessageEndpoint} from '../../service/rest/email-message.endpoint';
import {EmailMessageReadModel} from '../../service/rest/read-model/email-message.read-model';
import {Observable} from 'rxjs';
import {EmailStatisticsReadModel} from '../../service/rest/read-model/email-statistics.read-model';
import {EventSourcePolyfill} from 'ng-event-source';
import {environment} from '../../../../environments/environment';

@Component({
  selector: 'bda-admin-email-history',
  templateUrl: './email-history.component.html',
  styleUrls: ['./email-history.component.less']
})
export class EmailHistoryComponent implements OnInit {

  emailMessages$: Observable<EmailMessageReadModel[]>;
  emailStatistics$: Observable<EmailStatisticsReadModel>;


  constructor(private emailMessageEndpoint: EmailMessageEndpoint, private ngZone: NgZone) {
  }

  ngOnInit() {
    this.emailMessages$ = this.emailMessageEndpoint.getAllEmailMessage();
    this.emailStatistics$ = this.emailMessageEndpoint.getEmailMessagesStatistics(); //TODO: Make emailStatistics Observable merge with eventsource

    const eventSource = new EventSourcePolyfill(`${environment.restApi.baseUrl}/rest-api/v1/admin/email-message/stream`,{});
    console.log(eventSource);
    eventSource.onmessage = (event => {
      /*this.ngZone.run(() => {
        console.log(event);
      });*/
      this.emailMessages$ = this.emailMessageEndpoint.getAllEmailMessage();
      this.emailStatistics$ = this.emailMessageEndpoint.getEmailMessagesStatistics();
    });
  }

}

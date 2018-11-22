import {Component, NgZone, OnDestroy, OnInit} from '@angular/core';
import {EmailMessageEndpoint} from '../../service/rest/email-message.endpoint';
import {EmailMessageReadModel} from '../../service/rest/read-model/email-message.read-model';
import {Observable, Subscription} from 'rxjs';
import {EmailStatisticsReadModel} from '../../service/rest/read-model/email-statistics.read-model';
import {EventSourcePolyfill} from 'ng-event-source';
import {environment} from '../../../../environments/environment';
import {EventType} from '../../service/rest/event/event-type';

@Component({
  selector: 'bda-admin-email-history',
  templateUrl: './email-history.component.html',
  styleUrls: ['./email-history.component.less']
})
export class EmailHistoryComponent implements OnInit, OnDestroy {

  private subscription: Subscription;
  emailMessages: EmailMessageReadModel[];
  emailStatistics$: Observable<EmailStatisticsReadModel>;
  newMessagesAvailable = false;

  private eventSource: EventSourcePolyfill;


  constructor(private emailMessageEndpoint: EmailMessageEndpoint, private ngZone: NgZone) {
  }

  resendEmailMessage(emailMessageReadModel: EmailMessageReadModel) {
    emailMessageReadModel.status = 'SENDING';
    this.emailMessageEndpoint.resendEmailMessage(emailMessageReadModel.emailMessageLogId)
      .subscribe();
  }

  forwardEmailMessage(emailMessageReadModel: EmailMessageReadModel) {
    // this.emailMessageEndpoint.forwardEmailMessage(emailMessageReadModel.emailMessageLogId, {}).subscribe();
  }

  ngOnInit() {
    this.loadEmailMessages();
    this.loadEmailStatistics();
    this.observeEmailMessagesProjectedEvents();
  }

  private observeEmailMessagesProjectedEvents() {
    this.eventSource = new EventSourcePolyfill(
      `${environment.restApi.baseUrl}/rest-api/v1/admin/email-message/projected-events-stream`, {}
    );
    console.log(this.eventSource);
    this.eventSource.onmessage = (event => {
      const data: any = JSON.parse(event.data);
      /*this.ngZone.run(() => {
        console.log(event);
      });*/
      switch (data.eventType) {
        case EventType.EMAIL_MESSAGE_SENT_FAILURE: {
          const emailMessage = this.emailMessages.find(it => it.emailMessageLogId === data.payload.emailMessageLogId);
          emailMessage.status = 'FAIL_TO_SEND';
          break;
        }
        case EventType.EMAIL_MESSAGE_SENT_SUCCESS: {
          const emailMessage = this.emailMessages.find(it => it.emailMessageLogId === data.payload.emailMessageLogId);
          emailMessage.status = 'SENT';
          emailMessage.sentDate = data.payload.sentDate;
          break;
        }
        case EventType.EMAIL_MESSAGE_CREATED: {
          this.newMessagesAvailable = true;
          break;
        }
      }
      this.loadEmailStatistics();
    });
  }

  private loadEmailMessages() {
    this.newMessagesAvailable = false;
    this.subscription = this.emailMessageEndpoint.getAllEmailMessage().subscribe(messages => this.emailMessages = messages);
  }

  private loadEmailStatistics() {
    this.emailStatistics$ = this.emailMessageEndpoint.getEmailMessagesStatistics();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
    this.eventSource.close();
  }

}

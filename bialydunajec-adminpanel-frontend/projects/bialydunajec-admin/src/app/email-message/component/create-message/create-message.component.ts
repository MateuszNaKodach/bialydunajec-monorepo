import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {EmailMessageEndpoint} from '../../service/rest/email-message.endpoint';
import {SendEmailMessageRequest} from '../../service/rest/request/send-email-message.request';
import {finalize} from 'rxjs/operators';
import {AngularFormHelper} from '../../../../../../bialydunajec-main/src/app/shared/helper/angular-form.helper';

@Component({
  selector: 'bda-admin-create-message',
  templateUrl: './create-message.component.html',
  styleUrls: ['./create-message.component.less']
})
export class CreateMessageComponent implements OnInit {

  createMessageForm: FormGroup;

  submittingInProgress = false;


  constructor(private formBuilder: FormBuilder, private emailMessageEndpoint: EmailMessageEndpoint) {
  }

  ngOnInit() {
    this.initForm();
  }

  initForm() {
    const recipientsArray = new FormArray([]);

    this.createMessageForm = this.formBuilder.group({
      recipients: recipientsArray,
      subject: [null, [Validators.required]],
      content: [null, [Validators.required]]
    });

    this.addRecipient();
  }

  addRecipient(name: string = null, emailAddress: string = null) {
    (<FormArray>this.createMessageForm.get('recipients')).insert(0,
      new FormGroup({
        'name': new FormControl(name, []),
        'emailAddress': new FormControl(emailAddress, [Validators.required])
      })
    );
  }

  get subjectFormControl() {
    return this.createMessageForm.get('subject');
  }

  get contentFormControl() {
    return this.createMessageForm.get('content');
  }

  get recipientsFormControls() {
    return (<FormArray>this.createMessageForm.get('recipients')).controls;
  }

  deleteRecipientFormControl(controlIndex: number) {
    (<FormArray>this.createMessageForm.get('recipients')).removeAt(controlIndex);
  }

  submitEmailMessage() {
    AngularFormHelper.markFormGroupDirty(this.createMessageForm);
    if (!this.createMessageForm.valid) {
      return null;
    }


    const recipientEmailAddresses = this.recipientsFormControls.map(it => it.value.emailAddress);
    const requestBody = new SendEmailMessageRequest(
      recipientEmailAddresses,
      this.subjectFormControl.value,
      this.contentFormControl.value
    );
    this.submittingInProgress = true;
    this.emailMessageEndpoint.sendEmailMessage(requestBody)
      .pipe(
        finalize(() => this.submittingInProgress = false)
      )
      .subscribe(response => {
        this.subjectFormControl.reset();
        this.contentFormControl.reset();
      });
  }
}

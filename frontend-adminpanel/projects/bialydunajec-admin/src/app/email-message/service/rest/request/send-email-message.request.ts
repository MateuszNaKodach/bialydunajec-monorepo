export class SendEmailMessageRequest {
  emailAddresses: string[];
  subject: string;
  content: string;


  constructor(emailAddresses: string[], subject: string, content: string) {
    this.emailAddresses = emailAddresses;
    this.subject = subject;
    this.content = content;
  }
}

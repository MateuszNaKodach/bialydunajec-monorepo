export class EmailMessageReadModel {
  emailMessageLogId: string;
  recipient: string;
  subject: string;
  content: string;
  status: string;
  lastError: string;
  sentDate: Date;
  createdDate: Date;
}

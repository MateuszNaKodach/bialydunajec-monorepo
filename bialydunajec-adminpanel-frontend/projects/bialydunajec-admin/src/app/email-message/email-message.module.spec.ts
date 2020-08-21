import { EmailMessageModule } from './email-message.module';

describe('EmailMessageModule', () => {
  let emailMessageModule: EmailMessageModule;

  beforeEach(() => {
    emailMessageModule = new EmailMessageModule();
  });

  it('should create an instance', () => {
    expect(emailMessageModule).toBeTruthy();
  });
});

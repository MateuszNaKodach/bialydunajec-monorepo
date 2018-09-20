import { CampersRegistrationModule } from './campers-registration.module';

describe('CampersRegistrationModule', () => {
  let campersRegistrationModule: CampersRegistrationModule;

  beforeEach(() => {
    campersRegistrationModule = new CampersRegistrationModule();
  });

  it('should create an instance', () => {
    expect(campersRegistrationModule).toBeTruthy();
  });
});

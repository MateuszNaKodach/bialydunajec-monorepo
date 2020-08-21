import { CampEditionModule } from './camp-edition.module';

describe('CampEditionModule', () => {
  let campEditionModule: CampEditionModule;

  beforeEach(() => {
    campEditionModule = new CampEditionModule();
  });

  it('should create an instance', () => {
    expect(campEditionModule).toBeTruthy();
  });
});

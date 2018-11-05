import { CampNewsModule } from './camp-news.module';

describe('CampNewsModule', () => {
  let campNewsModule: CampNewsModule;

  beforeEach(() => {
    campNewsModule = new CampNewsModule();
  });

  it('should create an instance', () => {
    expect(campNewsModule).toBeTruthy();
  });
});

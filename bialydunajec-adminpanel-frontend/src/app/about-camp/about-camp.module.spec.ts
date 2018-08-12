import { AboutCampModule } from './about-camp.module';

describe('AboutCampModule', () => {
  let aboutCampModule: AboutCampModule;

  beforeEach(() => {
    aboutCampModule = new AboutCampModule();
  });

  it('should create an instance', () => {
    expect(aboutCampModule).toBeTruthy();
  });
});

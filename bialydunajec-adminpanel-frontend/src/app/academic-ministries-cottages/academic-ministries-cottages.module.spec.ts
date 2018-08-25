import { AcademicMinistriesCottagesModule } from './academic-ministries-cottages.module';

describe('CottagesModule', () => {
  let cottagesModule: AcademicMinistriesCottagesModule;

  beforeEach(() => {
    cottagesModule = new AcademicMinistriesCottagesModule();
  });

  it('should create an instance', () => {
    expect(cottagesModule).toBeTruthy();
  });
});

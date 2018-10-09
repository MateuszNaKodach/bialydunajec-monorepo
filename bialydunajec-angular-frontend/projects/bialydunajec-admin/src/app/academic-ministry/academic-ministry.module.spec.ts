import { AcademicMinistryModule } from './academic-ministry.module';

describe('AcademicMinistryModule', () => {
  let academicMinistryModule: AcademicMinistryModule;

  beforeEach(() => {
    academicMinistryModule = new AcademicMinistryModule();
  });

  it('should create an instance', () => {
    expect(academicMinistryModule).toBeTruthy();
  });
});

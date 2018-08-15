import { CottagesModule } from './cottages.module';

describe('CottagesModule', () => {
  let cottagesModule: CottagesModule;

  beforeEach(() => {
    cottagesModule = new CottagesModule();
  });

  it('should create an instance', () => {
    expect(cottagesModule).toBeTruthy();
  });
});

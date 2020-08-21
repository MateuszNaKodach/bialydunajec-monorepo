import { CampBusModule } from './camp-bus.module';

describe('CampBusModule', () => {
  let campBusModule: CampBusModule;

  beforeEach(() => {
    campBusModule = new CampBusModule();
  });

  it('should create an instance', () => {
    expect(campBusModule).toBeTruthy();
  });
});

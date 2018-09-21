import { TransportModule } from './transport.module';

describe('TransportModule', () => {
  let transportModule: TransportModule;

  beforeEach(() => {
    transportModule = new TransportModule();
  });

  it('should create an instance', () => {
    expect(transportModule).toBeTruthy();
  });
});

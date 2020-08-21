import { TestBed, inject } from '@angular/core/testing';

import { CampTransportService } from './camp-transport.service';

describe('CampTransportService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CampTransportService]
    });
  });

  it('should be created', inject([CampTransportService], (service: CampTransportService) => {
    expect(service).toBeTruthy();
  }));
});

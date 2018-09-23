import { TestBed, inject } from '@angular/core/testing';

import { BialyDunajecCommonsService } from './bialydunajec-commons.service';

describe('BialyDunajecCommonsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [BialyDunajecCommonsService]
    });
  });

  it('should be created', inject([BialyDunajecCommonsService], (service: BialyDunajecCommonsService) => {
    expect(service).toBeTruthy();
  }));
});

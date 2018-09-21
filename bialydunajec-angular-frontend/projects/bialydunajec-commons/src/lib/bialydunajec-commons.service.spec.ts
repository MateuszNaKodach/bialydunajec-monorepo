import { TestBed, inject } from '@angular/core/testing';

import { BialydunajecCommonsService } from './bialydunajec-commons.service';

describe('BialydunajecCommonsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [BialydunajecCommonsService]
    });
  });

  it('should be created', inject([BialydunajecCommonsService], (service: BialydunajecCommonsService) => {
    expect(service).toBeTruthy();
  }));
});

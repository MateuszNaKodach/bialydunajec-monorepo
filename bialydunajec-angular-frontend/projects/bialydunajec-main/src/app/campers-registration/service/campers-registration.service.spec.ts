import { TestBed, inject } from '@angular/core/testing';

import { CampersRegistrationService } from './campers-registration.service';

describe('CampersRegistrationService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CampersRegistrationService]
    });
  });

  it('should be created', inject([CampersRegistrationService], (service: CampersRegistrationService) => {
    expect(service).toBeTruthy();
  }));
});

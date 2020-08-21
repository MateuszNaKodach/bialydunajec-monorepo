import { TestBed, inject } from '@angular/core/testing';

import { CamperRegistrationFormStateService } from './camper-registration-form-state.service';

describe('CamperRegistrationFormDataService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CamperRegistrationFormStateService]
    });
  });

  it('should be created', inject([CamperRegistrationFormStateService], (service: CamperRegistrationFormStateService) => {
    expect(service).toBeTruthy();
  }));
});

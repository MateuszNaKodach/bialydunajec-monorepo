import { TestBed, inject } from '@angular/core/testing';

import { AcademicMinistryService } from './academic-ministry.service';

describe('AcademicMinistryService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AcademicMinistryService]
    });
  });

  it('should be created', inject([AcademicMinistryService], (service: AcademicMinistryService) => {
    expect(service).toBeTruthy();
  }));
});

import { TestBed, inject } from '@angular/core/testing';

import { CampCadreService } from './camp-cadre.service';

describe('CampCadreService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CampCadreService]
    });
  });

  it('should be created', inject([CampCadreService], (service: CampCadreService) => {
    expect(service).toBeTruthy();
  }));
});

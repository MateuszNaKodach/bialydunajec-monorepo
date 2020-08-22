import { TestBed, inject } from '@angular/core/testing';

import { CampEditionService } from './camp-edition.service';

describe('CampEditionService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CampEditionService]
    });
  });

  it('should be created', inject([CampEditionService], (service: CampEditionService) => {
    expect(service).toBeTruthy();
  }));
});

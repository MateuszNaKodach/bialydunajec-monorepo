import { TestBed, inject } from '@angular/core/testing';

import { CottageService } from './cottage.service';

describe('CottageService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CottageService]
    });
  });

  it('should be created', inject([CottageService], (service: CottageService) => {
    expect(service).toBeTruthy();
  }));
});

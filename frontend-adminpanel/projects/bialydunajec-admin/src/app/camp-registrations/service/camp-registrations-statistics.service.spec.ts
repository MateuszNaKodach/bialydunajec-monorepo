import { TestBed, inject } from '@angular/core/testing';

import { CampRegistrationsStatisticsService } from './camp-registrations-statistics.service';

describe('CampRegistrationsStatisticsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CampRegistrationsStatisticsService]
    });
  });

  it('should be created', inject([CampRegistrationsStatisticsService], (service: CampRegistrationsStatisticsService) => {
    expect(service).toBeTruthy();
  }));
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CampRegistrationsStatisticsComponent } from './camp-registrations-statistics.component';

describe('CampRegistrationsStatisticsComponent', () => {
  let component: CampRegistrationsStatisticsComponent;
  let fixture: ComponentFixture<CampRegistrationsStatisticsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CampRegistrationsStatisticsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CampRegistrationsStatisticsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrationSummaryComponent } from './registration-summary.component';

describe('RegistrationSummaryComponent', () => {
  let component: RegistrationSummaryComponent;
  let fixture: ComponentFixture<RegistrationSummaryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegistrationSummaryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegistrationSummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

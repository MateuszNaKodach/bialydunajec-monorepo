import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CampRegistrationsReminderComponent } from './camp-registrations-reminder.component';

describe('CampRegistrationsReminderComponent', () => {
  let component: CampRegistrationsReminderComponent;
  let fixture: ComponentFixture<CampRegistrationsReminderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CampRegistrationsReminderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CampRegistrationsReminderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

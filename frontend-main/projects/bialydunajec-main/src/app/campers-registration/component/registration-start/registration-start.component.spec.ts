import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrationStartComponent } from './registration-start.component';

describe('RegistrationStartComponent', () => {
  let component: RegistrationStartComponent;
  let fixture: ComponentFixture<RegistrationStartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegistrationStartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegistrationStartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

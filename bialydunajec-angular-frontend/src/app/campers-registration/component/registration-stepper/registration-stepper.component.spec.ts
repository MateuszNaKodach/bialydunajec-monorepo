import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrationStepperComponent } from './registration-stepper.component';

describe('RegistrationStepperComponent', () => {
  let component: RegistrationStepperComponent;
  let fixture: ComponentFixture<RegistrationStepperComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegistrationStepperComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegistrationStepperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormInputErrorComponent } from './form-input-error.component';

describe('FormInputErrorComponent', () => {
  let component: FormInputErrorComponent;
  let fixture: ComponentFixture<FormInputErrorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormInputErrorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormInputErrorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

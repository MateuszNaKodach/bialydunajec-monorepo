import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormInputValueComponent } from './form-input-value.component';

describe('FormInputValueComponent', () => {
  let component: FormInputValueComponent;
  let fixture: ComponentFixture<FormInputValueComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormInputValueComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormInputValueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

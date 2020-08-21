import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CampSignUpButtonComponent } from './camp-sign-up-button.component';

describe('CampSignUpButtonComponent', () => {
  let component: CampSignUpButtonComponent;
  let fixture: ComponentFixture<CampSignUpButtonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CampSignUpButtonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CampSignUpButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

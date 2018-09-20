import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormNavigationButtonsComponent } from './form-navigation-buttons.component';

describe('FormNavigationButtonsComponent', () => {
  let component: FormNavigationButtonsComponent;
  let fixture: ComponentFixture<FormNavigationButtonsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormNavigationButtonsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormNavigationButtonsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

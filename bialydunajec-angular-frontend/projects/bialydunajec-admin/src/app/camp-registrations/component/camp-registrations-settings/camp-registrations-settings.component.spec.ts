import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CampRegistrationsSettingsComponent } from './camp-registrations-settings.component';

describe('CampEditionSettingsComponent', () => {
  let component: CampRegistrationsSettingsComponent;
  let fixture: ComponentFixture<CampRegistrationsSettingsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CampRegistrationsSettingsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CampRegistrationsSettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

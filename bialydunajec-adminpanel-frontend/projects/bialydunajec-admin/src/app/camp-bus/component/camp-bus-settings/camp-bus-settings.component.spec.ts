import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CampBusSettingsComponent } from './camp-bus-settings.component';

describe('CampBusSettingsComponent', () => {
  let component: CampBusSettingsComponent;
  let fixture: ComponentFixture<CampBusSettingsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CampBusSettingsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CampBusSettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

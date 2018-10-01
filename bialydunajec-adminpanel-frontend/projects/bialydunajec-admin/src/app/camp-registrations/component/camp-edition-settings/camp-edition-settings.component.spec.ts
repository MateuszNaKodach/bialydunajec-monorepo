import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CampEditionSettingsComponent } from './camp-edition-settings.component';

describe('CampEditionSettingsComponent', () => {
  let component: CampEditionSettingsComponent;
  let fixture: ComponentFixture<CampEditionSettingsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CampEditionSettingsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CampEditionSettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

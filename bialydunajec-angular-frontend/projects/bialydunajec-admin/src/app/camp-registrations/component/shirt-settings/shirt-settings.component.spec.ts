import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShirtSettingsComponent } from './shirt-settings.component';

describe('ShirtSettingsComponent', () => {
  let component: ShirtSettingsComponent;
  let fixture: ComponentFixture<ShirtSettingsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShirtSettingsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShirtSettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DualToggleButtonComponent } from './dual-toggle-button.component';

describe('DualToggleButtonComponent', () => {
  let component: DualToggleButtonComponent;
  let fixture: ComponentFixture<DualToggleButtonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DualToggleButtonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DualToggleButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

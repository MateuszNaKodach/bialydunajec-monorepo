import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CottageStatusBadgeComponent } from './cottage-status-badge.component';

describe('CottageStatusBadgeComponent', () => {
  let component: CottageStatusBadgeComponent;
  let fixture: ComponentFixture<CottageStatusBadgeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CottageStatusBadgeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CottageStatusBadgeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

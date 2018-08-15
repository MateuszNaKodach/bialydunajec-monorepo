import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CottageDetailsComponent } from './cottage-details.component';

describe('CottageDetailsComponent', () => {
  let component: CottageDetailsComponent;
  let fixture: ComponentFixture<CottageDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CottageDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CottageDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

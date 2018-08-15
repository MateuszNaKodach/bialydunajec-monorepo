import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CottageMinistryDetailsComponent } from './cottage-ministry-details.component';

describe('CottageMinistryDetailsComponent', () => {
  let component: CottageMinistryDetailsComponent;
  let fixture: ComponentFixture<CottageMinistryDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CottageMinistryDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CottageMinistryDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

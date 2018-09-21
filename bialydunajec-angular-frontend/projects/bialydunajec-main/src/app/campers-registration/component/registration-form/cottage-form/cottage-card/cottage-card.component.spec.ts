import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CottageCardComponent } from './cottage-card.component';

describe('CottageCardComponent', () => {
  let component: CottageCardComponent;
  let fixture: ComponentFixture<CottageCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CottageCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CottageCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

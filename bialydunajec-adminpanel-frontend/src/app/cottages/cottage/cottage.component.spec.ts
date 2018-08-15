import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CottageComponent } from './cottage.component';

describe('CottageDetailsComponent', () => {
  let component: CottageComponent;
  let fixture: ComponentFixture<CottageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CottageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CottageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

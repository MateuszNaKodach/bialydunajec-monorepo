import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TransportInfoCarComponent } from './transport-info-car.component';

describe('TransportInfoCarComponent', () => {
  let component: TransportInfoCarComponent;
  let fixture: ComponentFixture<TransportInfoCarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TransportInfoCarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransportInfoCarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

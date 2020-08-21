import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeanOfTransportComponent } from './mean-of-transport.component';

describe('MeanOfTransportComponent', () => {
  let component: MeanOfTransportComponent;
  let fixture: ComponentFixture<MeanOfTransportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeanOfTransportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeanOfTransportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

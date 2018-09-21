import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeanOfTransportHeaderComponent } from './mean-of-transport-header.component';

describe('MeanOfTransportHeaderComponent', () => {
  let component: MeanOfTransportHeaderComponent;
  let fixture: ComponentFixture<MeanOfTransportHeaderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeanOfTransportHeaderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeanOfTransportHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TransportInfoCampBusComponent } from './transport-info-camp-bus.component';

describe('TransportInfoCampBusComponent', () => {
  let component: TransportInfoCampBusComponent;
  let fixture: ComponentFixture<TransportInfoCampBusComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TransportInfoCampBusComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransportInfoCampBusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

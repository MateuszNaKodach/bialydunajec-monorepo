import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CampBusReservationsComponent } from './camp-bus-reservations.component';

describe('CampBusReservationsComponent', () => {
  let component: CampBusReservationsComponent;
  let fixture: ComponentFixture<CampBusReservationsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CampBusReservationsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CampBusReservationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

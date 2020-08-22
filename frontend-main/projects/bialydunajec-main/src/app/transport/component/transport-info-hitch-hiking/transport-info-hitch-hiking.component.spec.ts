import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TransportInfoHitchHikingComponent } from './transport-info-hitch-hiking.component';

describe('TransportInfoHitchHikingComponent', () => {
  let component: TransportInfoHitchHikingComponent;
  let fixture: ComponentFixture<TransportInfoHitchHikingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TransportInfoHitchHikingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransportInfoHitchHikingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

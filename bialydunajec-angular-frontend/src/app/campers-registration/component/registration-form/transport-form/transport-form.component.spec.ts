import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TransportFormComponent } from './transport-form.component';

describe('TransportFormComponent', () => {
  let component: TransportFormComponent;
  let fixture: ComponentFixture<TransportFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TransportFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransportFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShirtOrdersComponent } from './shirt-orders.component';

describe('ShirtOrdersComponent', () => {
  let component: ShirtOrdersComponent;
  let fixture: ComponentFixture<ShirtOrdersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShirtOrdersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShirtOrdersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

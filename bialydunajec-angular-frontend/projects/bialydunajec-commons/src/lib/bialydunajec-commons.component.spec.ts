import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BialydunajecCommonsComponent } from './bialydunajec-commons.component';

describe('BialydunajecCommonsComponent', () => {
  let component: BialydunajecCommonsComponent;
  let fixture: ComponentFixture<BialydunajecCommonsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BialydunajecCommonsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BialydunajecCommonsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

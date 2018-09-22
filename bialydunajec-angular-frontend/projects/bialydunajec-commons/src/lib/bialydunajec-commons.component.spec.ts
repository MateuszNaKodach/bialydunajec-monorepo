import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BialyDunajecCommonsComponent } from './bialydunajec-commons.component';

describe('BialyDunajecCommonsComponent', () => {
  let component: BialyDunajecCommonsComponent;
  let fixture: ComponentFixture<BialyDunajecCommonsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BialyDunajecCommonsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BialyDunajecCommonsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

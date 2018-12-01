import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MoreMenuOptionsComponent } from './more-menu-options.component';

describe('MoreMenuOptionsComponent', () => {
  let component: MoreMenuOptionsComponent;
  let fixture: ComponentFixture<MoreMenuOptionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MoreMenuOptionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MoreMenuOptionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

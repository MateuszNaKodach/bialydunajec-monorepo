import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BottomBarMenuComponent } from './bottom-bar-menu.component';

describe('BottomBarMenuComponent', () => {
  let component: BottomBarMenuComponent;
  let fixture: ComponentFixture<BottomBarMenuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BottomBarMenuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BottomBarMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

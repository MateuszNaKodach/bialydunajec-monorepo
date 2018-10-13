import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PanelLayoutComponent } from './panel-layout.component';

describe('PanelLayoutComponent', () => {
  let component: PanelLayoutComponent;
  let fixture: ComponentFixture<PanelLayoutComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PanelLayoutComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PanelLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

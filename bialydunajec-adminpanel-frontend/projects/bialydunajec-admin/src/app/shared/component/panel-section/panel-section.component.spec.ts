import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PanelSectionComponent } from './panel-section.component';

describe('PanelSectionComponent', () => {
  let component: PanelSectionComponent;
  let fixture: ComponentFixture<PanelSectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PanelSectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PanelSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

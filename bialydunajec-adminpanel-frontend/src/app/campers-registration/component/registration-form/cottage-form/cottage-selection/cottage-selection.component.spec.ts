import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CottageSelectionComponent } from './cottage-selection.component';

describe('CottageSelectionComponent', () => {
  let component: CottageSelectionComponent;
  let fixture: ComponentFixture<CottageSelectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CottageSelectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CottageSelectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

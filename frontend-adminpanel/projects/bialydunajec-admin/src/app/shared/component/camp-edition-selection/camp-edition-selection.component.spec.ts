import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CampEditionSelectionComponent } from './camp-edition-selection.component';

describe('CampEditionSelectionComponent', () => {
  let component: CampEditionSelectionComponent;
  let fixture: ComponentFixture<CampEditionSelectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CampEditionSelectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CampEditionSelectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

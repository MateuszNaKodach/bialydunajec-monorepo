import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AcademicMinistrySelectionComponent } from './academic-ministry-selection.component';

describe('AcademicMinistrySelectionComponent', () => {
  let component: AcademicMinistrySelectionComponent;
  let fixture: ComponentFixture<AcademicMinistrySelectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AcademicMinistrySelectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AcademicMinistrySelectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

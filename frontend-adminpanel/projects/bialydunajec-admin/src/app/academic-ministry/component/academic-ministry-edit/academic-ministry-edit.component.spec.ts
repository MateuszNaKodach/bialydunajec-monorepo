import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AcademicMinistryEditComponent } from './academic-ministry-edit.component';

describe('AcademicMinistryEditComponent', () => {
  let component: AcademicMinistryEditComponent;
  let fixture: ComponentFixture<AcademicMinistryEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AcademicMinistryEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AcademicMinistryEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

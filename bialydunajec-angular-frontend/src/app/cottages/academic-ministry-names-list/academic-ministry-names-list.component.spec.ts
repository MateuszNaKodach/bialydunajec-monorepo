import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AcademicMinistryNamesListComponent } from './academic-ministry-names-list.component';

describe('AcademicMinistryNamesListComponent', () => {
  let component: AcademicMinistryNamesListComponent;
  let fixture: ComponentFixture<AcademicMinistryNamesListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AcademicMinistryNamesListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AcademicMinistryNamesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

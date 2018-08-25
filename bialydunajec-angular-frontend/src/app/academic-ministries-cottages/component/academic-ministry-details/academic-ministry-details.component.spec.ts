import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AcademicMinistryDetailsComponent } from './academic-ministry-details.component';

describe('CottageMinistryDetailsComponent', () => {
  let component: AcademicMinistryDetailsComponent;
  let fixture: ComponentFixture<AcademicMinistryDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AcademicMinistryDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AcademicMinistryDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

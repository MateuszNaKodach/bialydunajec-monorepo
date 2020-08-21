import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AcademicMinistryCardComponent } from './academic-ministry-card.component';

describe('AcademicMinistryCardComponent', () => {
  let component: AcademicMinistryCardComponent;
  let fixture: ComponentFixture<AcademicMinistryCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AcademicMinistryCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AcademicMinistryCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

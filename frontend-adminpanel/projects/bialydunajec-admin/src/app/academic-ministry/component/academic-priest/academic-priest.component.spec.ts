import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AcademicPriestComponent } from './academic-priest.component';

describe('AcademicPriestComponent', () => {
  let component: AcademicPriestComponent;
  let fixture: ComponentFixture<AcademicPriestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AcademicPriestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AcademicPriestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

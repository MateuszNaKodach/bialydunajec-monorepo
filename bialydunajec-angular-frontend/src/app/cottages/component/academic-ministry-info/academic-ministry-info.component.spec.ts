import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AcademicMinistryInfoComponent } from './academic-ministry-info.component';

describe('CottageDetailsComponent', () => {
  let component: AcademicMinistryInfoComponent;
  let fixture: ComponentFixture<AcademicMinistryInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AcademicMinistryInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AcademicMinistryInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

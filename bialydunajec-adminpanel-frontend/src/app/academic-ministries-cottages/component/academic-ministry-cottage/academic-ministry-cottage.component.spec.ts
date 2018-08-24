import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AcademicMinistryCottageComponent } from './academic-ministry-cottage.component';

describe('CottageDetailsComponent', () => {
  let component: AcademicMinistryCottageComponent;
  let fixture: ComponentFixture<AcademicMinistryCottageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AcademicMinistryCottageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AcademicMinistryCottageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

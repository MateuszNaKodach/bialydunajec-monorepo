import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AcademicMinistriesComponent } from './academic-ministries.component';

describe('AcademicMinistriesComponent', () => {
  let component: AcademicMinistriesComponent;
  let fixture: ComponentFixture<AcademicMinistriesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AcademicMinistriesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AcademicMinistriesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

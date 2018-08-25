import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AcademicMinistriesNamesComponent } from './academic-ministries-names.component';

describe('CottagesNamesListComponent', () => {
  let component: AcademicMinistriesNamesComponent;
  let fixture: ComponentFixture<AcademicMinistriesNamesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AcademicMinistriesNamesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AcademicMinistriesNamesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

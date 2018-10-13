import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CampEditionListComponent } from './camp-edition-list.component';

describe('CampEditionListComponent', () => {
  let component: CampEditionListComponent;
  let fixture: ComponentFixture<CampEditionListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CampEditionListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CampEditionListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

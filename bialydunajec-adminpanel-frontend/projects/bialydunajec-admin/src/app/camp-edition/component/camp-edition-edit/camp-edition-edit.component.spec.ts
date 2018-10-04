import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CampEditionEditComponent } from './camp-edition-edit.component';

describe('CampEditionEditComponent', () => {
  let component: CampEditionEditComponent;
  let fixture: ComponentFixture<CampEditionEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CampEditionEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CampEditionEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

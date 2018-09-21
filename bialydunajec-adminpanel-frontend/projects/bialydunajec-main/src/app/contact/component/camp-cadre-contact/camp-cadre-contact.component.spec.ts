import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CampCadreContactComponent } from './camp-cadre-contact.component';

describe('CampCadreContactComponent', () => {
  let component: CampCadreContactComponent;
  let fixture: ComponentFixture<CampCadreContactComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CampCadreContactComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CampCadreContactComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

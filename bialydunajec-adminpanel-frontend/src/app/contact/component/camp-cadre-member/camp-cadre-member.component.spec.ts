import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CampCadreMemberComponent } from './camp-cadre-member.component';

describe('CampCadreMemberComponent', () => {
  let component: CampCadreMemberComponent;
  let fixture: ComponentFixture<CampCadreMemberComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CampCadreMemberComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CampCadreMemberComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

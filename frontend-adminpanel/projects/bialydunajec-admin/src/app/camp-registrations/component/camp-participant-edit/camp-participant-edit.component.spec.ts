import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CampParticipantEditComponent } from './camp-participant-edit.component';

describe('CampParticipantEditComponent', () => {
  let component: CampParticipantEditComponent;
  let fixture: ComponentFixture<CampParticipantEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CampParticipantEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CampParticipantEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

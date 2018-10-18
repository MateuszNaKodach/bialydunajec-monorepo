import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CampParticipantListComponent } from './camp-participant-list.component';

describe('CampParticipantListComponent', () => {
  let component: CampParticipantListComponent;
  let fixture: ComponentFixture<CampParticipantListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CampParticipantListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CampParticipantListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

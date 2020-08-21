import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CampNewsListComponent } from './camp-news-list.component';

describe('CampNewsListComponent', () => {
  let component: CampNewsListComponent;
  let fixture: ComponentFixture<CampNewsListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CampNewsListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CampNewsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

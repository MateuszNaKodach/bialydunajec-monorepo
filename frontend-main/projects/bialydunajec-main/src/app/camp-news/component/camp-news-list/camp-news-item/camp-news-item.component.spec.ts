import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CampNewsItemComponent } from './camp-news-item.component';

describe('CampNewsItemComponent', () => {
  let component: CampNewsItemComponent;
  let fixture: ComponentFixture<CampNewsItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CampNewsItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CampNewsItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

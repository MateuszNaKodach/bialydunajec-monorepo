import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CottagesNamesListComponent } from './cottages-names-list.component';

describe('CottagesNamesListComponent', () => {
  let component: CottagesNamesListComponent;
  let fixture: ComponentFixture<CottagesNamesListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CottagesNamesListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CottagesNamesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CottageListComponent } from './cottage-list.component';

describe('CottageListComponent', () => {
  let component: CottageListComponent;
  let fixture: ComponentFixture<CottageListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CottageListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CottageListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

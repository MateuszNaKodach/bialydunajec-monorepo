import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CottageFormComponent } from './cottage-form.component';

describe('CottageFormComponent', () => {
  let component: CottageFormComponent;
  let fixture: ComponentFixture<CottageFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CottageFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CottageFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

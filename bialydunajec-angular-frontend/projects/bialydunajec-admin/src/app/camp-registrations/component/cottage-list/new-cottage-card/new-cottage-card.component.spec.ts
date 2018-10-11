import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewCottageCardComponent } from './new-cottage-card.component';

describe('NewCottageCardComponent', () => {
  let component: NewCottageCardComponent;
  let fixture: ComponentFixture<NewCottageCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewCottageCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewCottageCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

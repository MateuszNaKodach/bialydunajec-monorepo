import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FirstCampEditionInstructionComponent } from './first-camp-edition-instruction.component';

describe('FirstCampEditionInstructionComponent', () => {
  let component: FirstCampEditionInstructionComponent;
  let fixture: ComponentFixture<FirstCampEditionInstructionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FirstCampEditionInstructionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FirstCampEditionInstructionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

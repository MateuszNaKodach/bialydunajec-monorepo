import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShirtFormComponent } from './shirt-form.component';

describe('ShirtFormComponent', () => {
  let component: ShirtFormComponent;
  let fixture: ComponentFixture<ShirtFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShirtFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShirtFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

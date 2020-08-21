import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HttpResponseAlterComponent } from './http-response-alter.component';

describe('HttpResponseAlterComponent', () => {
  let component: HttpResponseAlterComponent;
  let fixture: ComponentFixture<HttpResponseAlterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HttpResponseAlterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HttpResponseAlterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

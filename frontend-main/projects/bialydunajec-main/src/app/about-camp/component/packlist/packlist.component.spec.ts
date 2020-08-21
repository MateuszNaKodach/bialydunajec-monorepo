import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PacklistComponent } from './packlist.component';

describe('PacklistComponent', () => {
  let component: PacklistComponent;
  let fixture: ComponentFixture<PacklistComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PacklistComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PacklistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

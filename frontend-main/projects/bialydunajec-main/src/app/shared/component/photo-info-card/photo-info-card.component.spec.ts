import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PhotoInfoCardComponent } from './photo-info-card.component';

describe('DetailsInfoCardComponent', () => {
  let component: PhotoInfoCardComponent;
  let fixture: ComponentFixture<PhotoInfoCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PhotoInfoCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PhotoInfoCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

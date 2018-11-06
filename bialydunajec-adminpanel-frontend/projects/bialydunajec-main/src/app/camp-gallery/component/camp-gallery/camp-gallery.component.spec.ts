import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CampGalleryComponent } from './camp-gallery.component';

describe('CampGalleryComponent', () => {
  let component: CampGalleryComponent;
  let fixture: ComponentFixture<CampGalleryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CampGalleryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CampGalleryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { CampGalleryModule } from './camp-gallery.module';

describe('CampGalleryModule', () => {
  let campGalleryModule: CampGalleryModule;

  beforeEach(() => {
    campGalleryModule = new CampGalleryModule();
  });

  it('should create an instance', () => {
    expect(campGalleryModule).toBeTruthy();
  });
});

import {browser, by, element} from 'protractor';
import {AppPage} from '../../app-page.po';

describe('main app header with menu', () => {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  it('header click should link to main page', () => {
    page.getHeaderCampLogo().click();
    expect(browser.getCurrentUrl()).toContain('/aktualnosci');
  });

});

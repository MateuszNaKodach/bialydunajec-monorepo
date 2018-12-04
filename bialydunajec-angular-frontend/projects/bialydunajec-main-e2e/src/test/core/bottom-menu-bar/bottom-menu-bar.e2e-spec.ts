import {browser, by, element} from 'protractor';
import {AppPage} from '../../app-page.po';

describe('interaction with bottom bar menu on smaller screen sizes', () => {
  let page: AppPage;

  beforeEach(() => {
    browser.driver.manage().window().setSize(320, 568);
    page = new AppPage();
  });

  it('bottom bar menu should be shown on smaller screens', () => {
    expect(page.getBottomNavBar().isDisplayed()).toBeTruthy();
  });

  it('bottom bar menu more option should open additional menu page', () => {
    page.getBottomNavBarMoreOption().click();
    expect(browser.getCurrentUrl()).toContain('/wiecej');
  });

});

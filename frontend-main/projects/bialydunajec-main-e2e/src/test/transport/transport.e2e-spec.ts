import {browser, by, element} from 'protractor';
import {TransportPage} from './transport.po';

describe('show information about camp transport', () => {
  let page: TransportPage;

  beforeEach(() => {
    browser.driver.manage().window().maximize();
    page = new TransportPage();
  });

  it('transport page should contain Google Map with marked camp bus departure place', () => {
    expect(page.getCampBusDepartureMarker().isDisplayed).toBeTruthy();
  });


});

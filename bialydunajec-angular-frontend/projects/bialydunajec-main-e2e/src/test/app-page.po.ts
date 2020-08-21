import {browser, by, element} from 'protractor';

export class AppPage {
  navigateToRoot() {
    return browser.get('/');
  }

  getBottomNavBar() {
    return element(by.className('bottom-nav'));
  }

  getBottomNavBarMoreOption() {
    return element(by.id('more-menu-link'));
  }

  getHeaderCampLogo() {
    return element(by.className('bda-logo'));
  }

  getTopNavMenu() {
    return element(by.className('navigation-items'));
  }
}

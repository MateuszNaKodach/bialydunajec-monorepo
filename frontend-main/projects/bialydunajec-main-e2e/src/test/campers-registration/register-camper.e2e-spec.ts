import {CampRegistrationsPage} from './camp-registrations.po';
import {browser, by, element} from 'protractor';

describe('camp participant registration by form', () => {
  let page: CampRegistrationsPage;

  beforeEach(() => {
    browser.driver.manage().window().maximize();
    page = new CampRegistrationsPage();
  });

  it(' user should be registered for a camp if all required inputs in form are filled properly', () => {
    page.navigateToRegistrationsStart();

    page.getSignUpFormButton().click();

    expect(browser.getCurrentUrl()).toContain('/zapisy/formularz/dane-osobowe');

    page.getMaleGenderButton().click();

    page.getFirstNameInput().sendKeys('Jan');
    page.getLastNameInput().sendKeys('Kowalski');
    page.getPeselInput().sendKeys('91071534644');
    page.getStreetInput().sendKeys('Grunwaldzka');
    page.getHomeNumberInput().sendKeys('12a/3');
    page.getPostalCodeInput().sendKeys('12-345');
    page.getCityInput().sendKeys('Wrocław');
    page.getEmailAddressInput().sendKeys('jan.kowalski@niepodam.pl');
    page.getPhoneNumberInput().sendKeys('123-456-789');

    page.getUniversityInput().sendKeys('Politechnika Wrocławska');
    page.getFacultyInput().sendKeys('Informatyki i Zarządzania');
    page.getFieldOfStudyInput().sendKeys('Informatyka');

    page.getKnowAboutCampFromSelection().click();
    element(by.id('knownAboutCampFrom1')).click();

    page.getOnCampForTimeSelection().click();
    element(by.id('onCampForTime1')).click();

    page.getCampRegulationsCheckbox().click();
    page.getCamperOwnResponsibilityCheckbox().click();
    page.getPersonalDataProcessingCheckbox().click();

    page.getNextStepButton().click();

    expect(browser.getCurrentUrl()).toContain('/zapisy/formularz/dojazd');

    page.getMeanOfTransportSelection().click();
    element(by.id('meanOfTransport0')).click();

    page.getNextStepButton().click();

    expect(browser.getCurrentUrl()).toContain('/zapisy/formularz/koszulka-obozowa');

    page.getShirtColorSelection().click();
    element(by.id('color0')).click();
    page.getMaleShirtTypeRadioButton().click();
    page.getShirtSizeSelection().click();
    element(by.id('size0')).click();
    page.getNextStepButton().click();

    expect(browser.getCurrentUrl()).toContain('/zapisy/formularz/chatka');

    element(by.id('cottage0')).click();

    page.getNextStepButton().click();

    expect(browser.getCurrentUrl()).toContain('/zapisy/formularz/podsumowanie');
  });


  it('user should not be registered if all required agreements are not checked', () => {
    page.navigateToRegistrationsStart();

    page.getSignUpFormButton().click();

    expect(browser.getCurrentUrl()).toContain('/zapisy/formularz/dane-osobowe');

    page.getMaleGenderButton().click();

    page.getFirstNameInput().sendKeys('Jan');
    page.getLastNameInput().sendKeys('Kowalski');
    page.getPeselInput().sendKeys('91071534644');
    page.getStreetInput().sendKeys('Grunwaldzka');
    page.getHomeNumberInput().sendKeys('12a/3');
    page.getPostalCodeInput().sendKeys('12-345');
    page.getCityInput().sendKeys('Wrocław');
    page.getEmailAddressInput().sendKeys('jan.kowalski@niepodam.pl');
    page.getPhoneNumberInput().sendKeys('123-456-789');

    page.getUniversityInput().sendKeys('Politechnika Wrocławska');
    page.getFacultyInput().sendKeys('Informatyki i Zarządzania');
    page.getFieldOfStudyInput().sendKeys('Informatyka');

    page.getKnowAboutCampFromSelection().click();
    element(by.id('knownAboutCampFrom1')).click();

    page.getOnCampForTimeSelection().click();
    element(by.id('onCampForTime1')).click();

    page.getCampRegulationsCheckbox().click();

    page.getNextStepButton().click();

    expect(browser.getCurrentUrl()).toContain('/zapisy/formularz/dane-osobowe');
  });

});

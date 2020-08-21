import {browser, by, element} from 'protractor';

export class CampRegistrationsPage {
  navigateToRegistrationsStart() {
    return browser.get('/zapisy/start');
  }

  getParagraphText() {
    return element(by.css('bda-root h1')).getText();
  }

  getSignUpFormButton() {
    return element(by.id('registrations-form-button'));
  }

  getMaleGenderButton() {
    return element(by.className('bda-dual-button-right'));
  }

  getFemaleGenderButton() {
    return element(by.className('bda-dual-button-left'));
  }

  getFirstNameInput() {
    return element(by.id('firstName-input'));
  }

  getLastNameInput() {
    return element(by.id('lastName-input'));
  }

  getPeselInput() {
    return element(by.id('pesel-input'));
  }

  getStreetInput() {
    return element(by.id('street-input'));
  }

  getHomeNumberInput() {
    return element(by.id('number-input'));
  }

  getPostalCodeInput() {
    return element(by.id('postalCode-input'));
  }

  getCityInput() {
    return element(by.id('city-input'));
  }

  getEmailAddressInput() {
    return element(by.id('email-input'));
  }

  getPhoneNumberInput() {
    return element(by.id('phoneNumber-input'));
  }

  getUniversityInput() {
    return element(by.id('university-input'));
  }

  getFacultyInput() {
    return element(by.id('faculty-input'));
  }

  getFieldOfStudyInput() {
    return element(by.id('fieldOfStudy-input'));
  }

  getIsRecentHighSchoolGraduateCheckbox() {
    return element(by.id('isRecentHighSchoolGraduate-checkbox'));
  }

  getHighSchoolInput() {
    return element(by.id('highSchool-input'));
  }

  getKnowAboutCampFromSelection() {
    return element(by.id('knowAboutCampFrom-selection'));
  }

  getOnCampForTimeSelection() {
    return element(by.id('onCampForTime-selection'));
  }

  getCampRegulationsCheckbox() {
    return element(by.id('campRegulations-checkbox'));
  }

  getCamperOwnResponsibilityCheckbox() {
    return element(by.id('camperOwnResponsibility-checkbox'));
  }

  getPersonalDataProcessingCheckbox() {
    return element(by.id('personalDataProcessing-checkbox'));
  }

  getMeanOfTransportSelection() {
    return element(by.id('meanOfTransport-selection'));
  }

  getShirtColorSelection() {
    return element(by.id('color-selection'));
  }

  getShirtSizeSelection() {
    return element(by.id('size-selection'));
  }

  getMaleShirtTypeRadioButton() {
    return element(by.id('maleClothTypeOption'));
  }

  getFemaleShirtTypeRadioButton() {
    return element(by.id('femaleClothTypeOption'));
  }

  getGoogleReCaptcha() {
    return element(by.className('recaptcha-checkbox-checkmark'));
  }

  getNextStepButton() {
    return element(by.className('next-step'));
  }
}

export class TestFixtures {


}

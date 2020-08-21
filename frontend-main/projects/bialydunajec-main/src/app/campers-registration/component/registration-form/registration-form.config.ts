import {campersRegistrationRoutingPaths} from '../../campers-registration-routing.paths';

export enum StepId {
  PERSONAL_DATA = 'PERSONAL_DATA',
  TRANSPORT = 'TRANSPORT',
  SHIRT = 'SHIRT',
  COTTAGE = 'COTTAGE'
}

export class RegistrationFormConfig {

  static STEPS_ORDER = [
    StepId.PERSONAL_DATA,
    StepId.TRANSPORT,
    StepId.SHIRT,
    StepId.COTTAGE
  ];

  static STEPS_CONFIG = [

    {
      stepId: StepId.PERSONAL_DATA,
      title: 'Dane osobowe',
      description: 'Wprowadź swoje dane',
      icon: 'clipboard list',
      relativeToFormPath: campersRegistrationRoutingPaths.personalData,
      showOnStepper: true
    }
    ,
    {
      stepId: StepId.TRANSPORT,
      title: 'Dojazd',
      description: 'Wybierz transport',
      icon: 'bus',
      relativeToFormPath: campersRegistrationRoutingPaths.transport,
      showOnStepper: true
    }
    ,
    {
      stepId: StepId.SHIRT,
      title: 'Koszulka',
      description: 'Wybierz kolor i rozmiar',
      icon: 'child',
      relativeToFormPath: campersRegistrationRoutingPaths.shirt,
      showOnStepper: true
    }
    ,
    {
      stepId: StepId.COTTAGE,
      title: 'Chatka',
      description: 'Wybierz swoją chatkę',
      icon: 'home',
      relativeToFormPath: campersRegistrationRoutingPaths.cottage,
      showOnStepper: true
    }
  ];

  static getStepConfigById(stepId: StepId) {
    return this.STEPS_CONFIG.find(step => step.stepId === stepId);
  }

  static getFirstStepOrder() {
    return 0;
  }

  static getLastStepOrder() {
    return RegistrationFormConfig.STEPS_ORDER.length - 1;
  }
}

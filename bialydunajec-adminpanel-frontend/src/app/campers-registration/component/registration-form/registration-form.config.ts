export enum StepId {
  PERSONAL_DATA = 'PERSONAL_DATA',
  TRANSPORT = 'TRANSPORT',
  SHIRT = 'SHIRT',
  COTTAGE = 'COTTAGE'
}

export const getRegistrationFormSteps = () => {
  return REGISTRATION_FORM_STEPS;
};

const REGISTRATION_FORM_STEPS: RegistrationFormStep[] = [

];

export const STEPS_ORDER = [
  StepId.PERSONAL_DATA,
  StepId.TRANSPORT,
  StepId.SHIRT,
  StepId.COTTAGE
];


export class RegistrationFormStep {
  id: StepId;
  title: string;
  description: string;
  icon: string;
  path: string;
}


import {StepId} from '../registration-form.config';

export class RegistrationStepViewModel {
  private readonly stepId: StepId;
  private readonly icon: string;
  private readonly title: string;
  private readonly description: string;
  private readonly relativeToFormPath: string;
  private completed: boolean;
  private disabled: boolean;

  constructor(stepId: StepId, title: string, icon: string = null, description: string = null, relativeToFormPath: string = null) {
    this.stepId = stepId;
    this.title = title;
    this.icon = icon;
    this.description = description;
    this.relativeToFormPath = relativeToFormPath;
  }

  getStepId(): StepId {
    return this.stepId;
  }

  getIcon(): string {
    return this.icon;
  }

  hasIcon(): boolean {
    return this.icon != null;
  }

  getTitle(): string {
    return this.title;
  }

  getDescription(): string {
    return this.description;
  }

  hasDescription(): boolean {
    return this.description != null;
  }

  getRelativeToFormPath(): string {
    return this.relativeToFormPath;
  }

  isCompleted() {
    return this.completed;
  }

  markAsCompleted() {
    this.completed = true;
  }

  markAsUncompleted() {
    this.completed = false;
  }

  isEnabled() {
    return !this.disabled;
  }

  isDisabled() {
    return !this.disabled;
  }

  disable() {
    this.disabled = true;
  }

  enable() {
    this.disabled = false;
  }
}

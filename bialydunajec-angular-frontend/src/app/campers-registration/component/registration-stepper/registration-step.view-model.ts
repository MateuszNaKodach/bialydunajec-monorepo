export class RegistrationStepViewModel {
  private readonly icon: string;
  private readonly title: string;
  private readonly description: string;
  private selected: boolean;
  private completed: boolean;
  private disabled: boolean;

  constructor(title: string, icon: string = null, description: string = null) {
    this.title = title;
    this.icon = icon;
    this.description = description;
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

  isSelected() {
    return this.selected;
  }

  select() {
    this.selected = true;
  }

  unselect() {
    this.selected = false;
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

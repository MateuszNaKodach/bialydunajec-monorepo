export class AlertViewModel {
  type?: string;
  message?: string;
  description?: string;

  constructor(type: string, message: string, description: string) {
    this.type = type;
    this.message = message;
    this.description = description;
  }
}

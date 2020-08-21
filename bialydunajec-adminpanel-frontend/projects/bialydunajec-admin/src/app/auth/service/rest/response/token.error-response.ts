export class TokenErrorResponse {
  error: string;
  error_description: string;

  constructor(error: string, error_description: string) {
    this.error = error;
    this.error_description = error_description;
  }
}

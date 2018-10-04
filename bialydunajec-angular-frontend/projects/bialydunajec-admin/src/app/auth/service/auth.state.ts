export class AuthState {
  currentUser: { username: string, emailAddress: string };
  errorMessage: string;


  constructor(currentUser: { username: string; emailAddress: string } = null, errorMessage: string = null) {
    this.currentUser = currentUser;
    this.errorMessage = errorMessage;
  }
}

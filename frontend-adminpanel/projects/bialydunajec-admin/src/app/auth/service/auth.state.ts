export class AuthState {
  currentUser: { userId: string, username: string, emailAddress: string };
  errorMessage: string;


  constructor(currentUser: { userId: string; username: string; emailAddress: string } = null, errorMessage: string = null) {
    this.currentUser = currentUser;
    this.errorMessage = errorMessage;
  }
}

export class UserViewModel {
  readonly userId: string;
  readonly firstName: string;
  readonly lastName: string;
  readonly emailAddress: string;

  constructor(userId: string, firstName: string, lastName: string, emailAddress: string) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailAddress = emailAddress;
  }
}

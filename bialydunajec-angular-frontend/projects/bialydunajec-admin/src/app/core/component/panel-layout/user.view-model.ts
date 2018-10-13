export class UserViewModel {
  readonly userId: string;
  readonly displayName: string;
  readonly firstName: string;
  readonly lastName: string;
  readonly emailAddress: string;


  constructor(userId: string, displayName: string, emailAddress: string, firstName: string = null, lastName: string = null) {
    this.userId = userId;
    this.displayName = displayName;
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailAddress = emailAddress;
  }

  get details() {
    let result = this.emailAddress;
    if (this.firstName && this.lastName && this.displayName !== this.firstName + ' ' + this.lastName) {
      result = result + ' (' + this.firstName + ' ' + this.lastName + ')';
    }
    return result;
  }
}

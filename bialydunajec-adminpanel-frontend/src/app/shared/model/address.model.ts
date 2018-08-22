export class Address {
  street: string;
  number: string;
  postalCode: string;
  city: string;

  constructor(street: string = '', number: string = '', postalCode: string = '', city: string = '') {
    this.street = street;
    this.number = number;
    this.city = city;
    this.postalCode = postalCode;
  }
}

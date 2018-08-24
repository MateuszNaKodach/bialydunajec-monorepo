export class Address {
  street: string;
  number: string;
  postalCode: string;
  city: string;
  notes: string;

  constructor(street: string = '', number: string = '', postalCode: string = '', city: string = '', notes: string = '') {
    this.street = street;
    this.number = number;
    this.city = city;
    this.postalCode = postalCode;
    this.notes = notes;
  }
}

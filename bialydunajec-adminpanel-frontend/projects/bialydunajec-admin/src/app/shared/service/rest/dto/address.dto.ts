export class AddressDto {
  street?: string;
  homeNumber?: string = null;
  city: string;
  postalCode?: string = null;


  constructor(street: string, homeNumber: string, city: string, postalCode: string) {
    this.street = street;
    this.homeNumber = homeNumber;
    this.city = city;
    this.postalCode = postalCode;
  }
}

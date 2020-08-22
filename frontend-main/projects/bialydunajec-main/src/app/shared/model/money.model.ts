export class Money {
  value: number;
  currencyCode: string;

  constructor(value: number, currencyCode: string) {
    this.value = value;
    this.currencyCode = currencyCode;
  }

  toString() {
    return this.value + ' ' + this.currencyCode;
  }
}

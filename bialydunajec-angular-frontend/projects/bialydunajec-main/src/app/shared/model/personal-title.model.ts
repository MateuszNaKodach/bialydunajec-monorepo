export class PersonalTitle {
  name: string;
  prefix: string;
  postfix: string;

  constructor(name: string, prefix: string, postfix: string = '') {
    this.name = name;
    this.prefix = prefix;
    this.postfix = postfix;
  }

  static none() {
    return new PersonalTitle('', '', '');
  }
}

export class PersonalTitleDto {
  name?: string;
  prefix?: string;
  postfix?: string;


  constructor(prefix: string, postfix: string, name: string = null) {
    this.name = name;
    this.prefix = prefix;
    this.postfix = postfix;
  }
}

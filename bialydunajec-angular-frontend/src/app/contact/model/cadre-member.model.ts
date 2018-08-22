export class CadreMember {
  id: string;
  firstName: string;
  lastName: string;
  responsibility: string;
  academicMinistryName: string;
  photoUrl: string;
  contactInfo: ContactInfo;
  description: PersonalDescription;

  constructor(id: string,
              firstName: string,
              lastName: string,
              responsibility: string,
              academicMinistryName: string,
              photoUrl: string,
              contactInfo: ContactInfo = null,
              description: PersonalDescription = PersonalDescription.empty()) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.responsibility = responsibility;
    this.academicMinistryName = academicMinistryName;
    this.photoUrl = photoUrl;
    this.contactInfo = contactInfo;
    this.description = description;
  }
}

export class ContactInfo {
  email: string;
  phoneNumber: string;
  facebookUrl: string;


  constructor(email: string = '', phoneNumber: string = '', facebookUrl: string = '') {
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.facebookUrl = facebookUrl;
  }
}

export class PersonalDescription {
  title: string;
  content: string;


  constructor(title: string, content: string) {
    this.title = title;
    this.content = content;
  }

  static empty() {
    return new PersonalDescription('', '');
  }

  isEmpty() {
    return this.content == null || this.content.length === 0;
  }
}

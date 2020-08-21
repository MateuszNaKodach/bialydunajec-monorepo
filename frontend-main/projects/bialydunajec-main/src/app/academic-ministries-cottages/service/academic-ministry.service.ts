import {Injectable} from '@angular/core';
import {AcademicMinistryDetails} from '../model/academic-ministry-details.model';
import {Address} from '../../shared/model/address.model';
import {Priest} from '../model/priest.model';
import {PersonalTitle} from '../../shared/model/personal-title.model';
import {ExtendedDescription} from '../../shared/model/extended-description.model';
import {Facebook} from '../../shared/model/facebook.model';
import {AcademicMinistry} from '../model/academic-ministry.model';
import {AcademicMinistryEndpoint} from './rest/academic-ministry.endpoint';

@Injectable()
export class AcademicMinistryService {

  constructor(private academicMinistryEndpoint: AcademicMinistryEndpoint) {
  }

  getAllAcademicMinistryDetails() {
    return [
      new AcademicMinistryDetails(
        '3',
        'Franciszkańskie Duszpasterstwo Akademickie Antoni',
        'Antoni',
        'assets/images/logo/antoni.png',
        new Address('al. Kasprowicza', '26', '51-137', 'Wrocław'),
        'http://www.antoni.org.pl/',
        null,
        'antoni@anotni.org',
        [
          new Priest(
            '12',
            'Oskar',
            'Maciaczyk',
            new PersonalTitle('ojciec', 'o.', 'OFM'),
            'assets/images/temp-placeholders/o_oskar.jpg',
            'oskar@anotni.org'
          )
        ],
        null,
        'assets/images/temp-placeholders/kosciol_antoni.jpg'
      ),
      new AcademicMinistryDetails(
        '6',
        'Duszpasterstwo Akademickie Dach',
        'Dach',
        'assets/images/logo/dach.png',
        new Address('ul. Wincentego Stysia', '16', '53-526', 'Wrocław - Parafia św. Ignacego Loyoli'),
        null,
        null,
        null
      ),
      new AcademicMinistryDetails(
        '9',
        'Dominikańskie Duszpasterstwo Akademickie Dominik',
        'Dominik',
        'assets/images/logo/dominik.png',
        new Address('pl. Dominikański', '2', '50-159', 'Wrocław'),
        null,
        null,
        null
      ),
      new AcademicMinistryDetails(
        '18',
        'Centralny Ośrodek Duszpasterstwa Akademickiego Maciejówka',
        'Maciejówka',
        'assets/images/logo/maciejowka.png',
        new Address('pl. bp. Nankiera', '17a', '50-140', 'Wrocław'),
        'http://www.maciejowka.org/',
        null,
        null
      ),
      new AcademicMinistryDetails(
        '21',
        'Duszpasterstwo Akademickie Redemptor',
        'Redemptor',
        'assets/images/logo/redemptor.png',
        new Address('ul. Wittiga', '11', null, 'Wrocław'),
        'da.redemptor.pl',
        new Facebook('https://www.facebook.com/da.redemptor'),
        'koordynator@da.redemptor.pl',
        [
          new Priest(
            '12',
            'Mariusz',
            'Mazurkiewicz',
            new PersonalTitle('ojciec', 'o.', 'CSSR'),
            'https://pbs.twimg.com/profile_images/643644948865134592/0OquP-Ih_400x400.jpg',
            'duszpasterz@da.redemptor.pl',
            '123-123-123'
          )
        ]
      ),
    ];
  }

  getAllAcademicMinistry() {
    return this.getAllAcademicMinistryDetails()
      .map(ministry => ministry.toAcademicMinistry());
  }

  getAllAcademicMinistryShortNames() {
    return this.getAllAcademicMinistryDetails()
      .map(ministry => ministry.shortName);
  }

  getAcademicMinistryDetailsById(id: string): AcademicMinistryDetails {
    return this.getAllAcademicMinistryDetails()
      .find(academicMinistry => academicMinistry.id === id);
  }

  getAcademicMinistryById(id: string): AcademicMinistry {
    return this.getAcademicMinistryDetailsById(id)
      .toAcademicMinistry();
  }

  getAcademicMinistryDetailsByShortName(shortName: string): AcademicMinistryDetails {
    return this.getAllAcademicMinistryDetails()
      .find(academicMinistry => academicMinistry.shortName === shortName);
  }
}

import {Injectable} from '@angular/core';
import {CadreMember, ContactInfo} from '../model/cadre-member.model';
import {ExtendedDescription} from '../../shared/model/extended-description.model';

@Injectable()
export class CampCadreService {

  private campCadreMembers = [
    new CadreMember(
      'asdasd-asdasd-ccccc-kopyto',
      'Piotr',
      'Kopyto',
      'Szef Obozu',
      'Redemptor',
      'https://bialydunajec.org/images/stories/kontakty/kadra_glowna/szef.jpg',
      new ContactInfo('szef@bialydunajec.org', '999-888-777'),
      new ExtendedDescription('Cześć!', 'Hej! Jestem szefem wszystkich szefów!')
    ),
    new CadreMember(
      'bbasd-asdasd-ccccc-sawcia',
      'Kinga',
      'Sawicka',
      'Rzecznik Obozu',
      'Dach',
      'https://bialydunajec.org/images/stories/kontakty/kadra_glowna/rzecznik.jpg',
      new ContactInfo('rzecznik@bialydunajec.org')
    ),
    new CadreMember(
      'bbasd-sss-ccccc-angela',
      'Angelika',
      'Majda',
      'Rzecznik Obozu',
      'Stygmatyk',
      'https://bialydunajec.org/images/stories/kontakty/kadra_glowna/kulturalna.jpg',
      null,
      new ExtendedDescription('Aloha!', 'To nie ja jestem szefem wszystkich szefów... :( Lorem Ipsum jest tekstem stosowanym jako przykładowy wypełniacz w przemyśle poligraficznym. Został po raz pierwszy użyty w XV w. przez nieznanego drukarza do wypełnienia tekstem próbnej książki. Pięć wieków później zaczął być używany przemyśle elektronicznym, pozostając praktycznie niezmienionym. Spopularyzował się w latach 60. XX w. wraz z publikacją arkuszy Letrasetu, zawierających fragmenty Lorem Ipsum, a ostatnio z zawierającym różne wersje Lorem Ipsum oprogramowaniem przeznaczonym do realizacji druków na komputerach osobistych, jak Aldus PageMaker')
    ),
    new CadreMember(
      'bbasd-asdasd-ccccc-sawcia',
      'Test',
      'SkądOnTutajBezZdjęcia',
      'Zwykły Szary Obozowicz',
      'Nieznane',
      null,
      new ContactInfo('camper@bialydunajec.org')
    ),
  ];

  constructor() {
  }

  getCampCadreMembers() {
    return this.campCadreMembers.slice();
  }
}

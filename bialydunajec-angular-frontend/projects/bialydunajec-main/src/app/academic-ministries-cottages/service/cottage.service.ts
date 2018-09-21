import {Injectable} from '@angular/core';
import {CottageDetails, CottageStayConditions, StayCondition} from '../model/cottage-details.model';
import {Address} from '../../shared/model/address.model';
import {CottageBoss} from '../model/cottage-boss.model';

@Injectable()
export class CottageService {

  constructor() {
  }

  getAllAcademicMinistriesCottages() {
    return [
      new CottageDetails(
        'asdasCottageId123',
        '3',
        'Antoni',
        'Domek Antoniego',
        'assets/images/temp-placeholders/antoni_chatka_235.jpg',
        new Address('ul. Jana Pawła II', '341a', '34-425', 'Biały Dunajec'),
        new CottageBoss(
          '12dasdaqwe',
          'Dominika',
          'Piguła',
          '123-123-123',
          'dominikapigula@gmail.com',
          'Politechnika Wrocławska',
          'Inżynieria chemiczna i procesowa',
          'assets/images/temp-placeholders/antoni_szef_235.jpg'
        ),
        new CottageStayConditions(
          new StayCondition('Jak jemy?', 'Jemy 3 posiłki dziennie: śniadanie,' +
            ' obiad i kolację. Dodatkowo na każdą trasę zabieramy suchy prowiant.'),
          new StayCondition('Jak śpimy?', 'W pokojach od 2- do 6- osobowych.\n' +
            '          Każdy pokój posiada osobną łazienkę.\n' +
            '          Na niektórych piętrach posiadamy dodatkowe łazienki.'),
          new StayCondition('30/55', 'Ilość zajętych miejsc w tej chatce. Śpiesz się! :)')
        )
      ),
      new CottageDetails(
        'asdasCottageId123',
        '21',
        'Redemptor',
        'Dom Wariatów Pana Pańszczyka',
        'assets/images/temp-placeholders/redemptor_chatka_235.jpg',
        new Address('ul. Jana Pawła II', '123b', '11-344', 'Biały Dunajec'),
        new CottageBoss(
          '12dasd12aqwe',
          'Kamil',
          'Kubacka',
          null,
          'bialydunajec@da.redemptor.pl',
          null,
          null,
          'assets/images/temp-placeholders/redemptor_szef_235.jpg'
        ),
        new CottageStayConditions(
          new StayCondition('Jakie jest jedzenie?', 'Jakie jedzenie jest, każdy widzi.'),
          new StayCondition('Jak śpimy?', 'Na Białym nie wiemy co to spanie!')
        )
      )
    ];
  }

  getCottageByAcademicMinistryId(academicMinistryId: string): CottageDetails {
    return this.getAllAcademicMinistriesCottages()
      .find(cottage => cottage.academicMinistryId === academicMinistryId);
  }
}

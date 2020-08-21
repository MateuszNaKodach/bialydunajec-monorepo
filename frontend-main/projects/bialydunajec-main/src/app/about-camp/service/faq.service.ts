import {Injectable} from '@angular/core';
import {FaqCategory} from '../model/faq-category.model';

@Injectable()
export class FaqService {

  private categories: FaqCategory[] = [
    {
      id: '1',
      name: 'Zapisy na obóz',
      questions: [
        {
          id: '1',
          content: 'Kto może jechać na obóz?',
          answer: 'Każdy, kto w październiku b.r. rozpocznie studia, bądź jest już studentem starszych lat, bez znaczenia czy są to studia dzienne czy zaoczne, we Wrocławiu, Opolu lub jest studentem innej uczelni, ale związany jest z Górą Świętej Anny.'
        },
        {
          id: '2',
          content: 'Czy trzeba być członkiem duszpasterstwa, żeby zapisać się na obóz?',
          answer: 'Nie. Obóz jest szczególnie przeznaczony dla osób nie będących w duszpasterstwie. Na obóz można przyjechać bez względu na to, czy jest się czy nie w duszpasterstwie akademickim. Jeśli się nie jest, to obóz jest doskonałą okazją, żeby zobaczyć, cóż to takiego jest to duszpasterstwo.'
        },
        {
          id: '3',
          content: 'Czym jest chatka?',
          answer: 'Zagadkowo brzmiące „chaty/chatki” to miejsca naszego zakwaterowania. To góralskie, głównie drewniane domy, w których można poznać prawdziwego Gazdę i Gaździnę. Widok z okna na Tatry gwarantowany! Każde Duszpasterstwo Akademickie zamieszkuje osobną chatkę. Podczas zapisów każdy uczestnik ma możliwość wyboru chatki, w której będzie mieszkał podczas Obozu. Zastanawiasz się, którą chatkę wybrać? Polecamy przeglądanie stron internetowych, fanpage’y DA, gdzie znajduje się więcej informacji. Możesz też wybrać Duszpasterstwo znajdujące się najbliżej Twojego miejsca zamieszkania we Wrocławiu. Przy zapisach znajduję się również opcja losowego wyboru chaty, doskonała opcja dla niezdecydowanych.'
        },
        {
          id: '4',
          content: 'Jakie dane są potrzebne przy zapisach?',
          answer: 'Oczywiście imię, nazwisko, płeć, nr telefonu, e-mail, adres zamieszkania (w przypadku osób mieszkających i zameldowanych w innych miejscach prosimy podać adres zameldowania). Bardzo ważny jest numer PESEL – potrzebny do ubezpieczenia uczestników obozu. Dane na temat studiów – nazwa uczelni, wydziału, kierunku, roku – są potrzebne do naszych organizacyjnych statystyk. Rozmiar i kolor koszulki (każdy uczestnik dostaje koszulkę obozową). Prosimy o dokładne wypełnianie formularza zgłoszeniowego!'
        },
        {
          id: '5',
          content: 'Nie otrzymałem maila potwierdzającego rejestrację. Co mam zrobić?',
          answer: 'Sprawdź czy kliknąłeś w link potwierdzający. Sprawdź koniecznie folder spam na swojej poczcie. Jeśli nadal nie dostałeś linka potwierdzającego zgłoście się do administratora zapisów: zapisy@bialydunajec.org'
        },
        {
          id: '6',
          content: 'Czy mogę przepisać się do innej chaty?',
          answer: 'Jedynie w wyniku obustronnego porozumienia się szefów obu chat - tej z której rezygnujesz oraz tej do której się zapisujesz. Przepisanie się jest możliwe jedynie do 14 dni przed obozem. Przepisania dokonuje administrator zapisów: zapisy@bialydunajec.org. Przepisywanie się w trakcie obozu nie jest możliwe!'
        },
        {
          id: '7',
          content: 'Czy można skrócić pobyt na obozie?',
          answer: 'Oczywiście! W kwestii wysokości opłaty za skrócony okres pobytu proszę kontaktować się bezpośrednio z szefem chaty.'
        },
        {
          id: '8',
          content: 'Czy na obóz może pojechać osoba niepełnosprawna?',
          answer: 'Decyduje szef chaty.'
        }
      ]
    },
    {
      id: '2',
      name: 'Kwestie finansowe',
      questions: [
        {
          id: '1',
          content: ' Ile kosztuje udział w Obozie?',
          answer: ' 419zł - cena obejmuje zakwaterowanie, wyżywienie, ubezpieczenie - cena nie obejmuje dojazdu do Białego Dunajca oraz kosztów przejazdów na obozie, opłat za bilety wstępu do TPN, itp. Wcześniej w ramach potwierdzenia udziału wpłacany jest zadatek w wysokości 99 zł.'
        },
        {
          id: '1',
          content: ' Do kiedy należy wpłacić zadatek?',
          answer: 'Zadatek należy wpłacić w terminie 7 dni od rejestracji. W przypadku braku wpłaty w terminie, miejsce zostaje oznaczone jako zwolnione! Zadatek zaliczany jest oczywiście na poczet ceny obozu.'
        },
        {
          id: '2',
          content: 'Czy w trakcie wyjazdu są dodatkowe wydatki?',
          answer: 'Tak. Należy liczyć się w kosztami dojazdów w góry. Z Białego Dunajca do Zakopanego i z powrotem. Ponadto ulgowy koszt wstępu to TPNu wynosi jednorazowo 2,5zł (karta Wielkiej Rodziny zapewnia darmowy wstęp).'
        },
        {
          id: '2',
          content: 'Nie mogę pojechać na obóz - czy zadatek jest zwracany?',
          answer: 'Zgodnie z regulaminem obozu, zarządza się co następuje: "Zadatek nie podlega zwrotowi w przypadku wycofania zapisu przez Uczestnika w czasie równym lub krótszym dwóm tygodniom poprzedzającym Obóz."'
        },
        {
          id: '2',
          content: 'Jadę na obóz - czy zadatek jest zwracany?',
          answer: 'Zadatek jest wliczany w ramach rozliczenia obozu. Czyli na miejscu dopłacasz szefowi chaty wysokość ceny obozu pomniejszoną o wartość zaliczki.'
        },
        {
          id: '2',
          content: 'Czy w cenie obozu jest dojazd?',
          answer: 'Nie, cena obozu nie obejmuje kosztów dojazdu zarówno do jak i z Białego Dunajca jak też kosztów dojazdów w góry. Fundusz na dojazdy "na miejscu" to ok. 100 - 150zł - niestety, górale mają swoje ceny :('
        },
        {
          id: '2',
          content: 'W jaki sposób należy wpłacić zadatek?',
          answer: 'Zadatek należy wpłacić na konto. Dane do przelewu zostaną wysłane na maila po potwierdzeniu rezerwacji. W tytule przelewu proszę pamiętać o podaniu imienia i nazwiska (według wzorca podanego przez szefa chaty)! Więcej informacji dot. płatności znajduje się w w-w mailu.'
        }
      ]
    },
    {
      id: '3',
      name: 'Warunki bytowe',
      questions: [
        {
          id: '2',
          content: 'Jak wyglądają kwestie żywienia?',
          answer: 'W każdej chacie jest kilka posiłków w ciągu dnia: śniadanie, wyprawka w góry oraz obiadokolacja (+ obiad i kolacja w dniu bez wyjścia w góry). Posiłki przygotowujemy sami podczas dyżurów kuchennych pod okiem osób funkcyjnych - „Kuchennych”. W kuchni dojadamy po godzinach ;)'
        },
        {
          id: '2',
          content: 'Czy w chatach jest ciepła woda?',
          answer: 'Oczywiście! Przecież mamy XXI wiek! A czy będzie leciała z kranu przez 24h/dobę, w części chat zależy to od dyżuru kuchennego (trzeba dopilnować pieca CWU).'
        },
        {
          id: '2',
          content: 'Czy łazienki są w pokojach?',
          answer: 'W większości chat - tak.'
        },
        {
          id: '2',
          content: 'Czy jest dostęp do internetu?',
          answer: 'W niektórych chatach jest WiFi, ale nie we wszystkich. W przypadku terminu zapisów uczelnianych przypadających na czas obozu można albo skontaktować się z szefem chaty w której jest internet albo będąc w Zakopanem skorzystać z kafejki internetowej. Na pewno nie warto wyjeżdżać w tym celu do Wrocławia.'
        },
        {
          id: '2',
          content: 'Czy na obozie jest dostępna pralka/żelazko?',
          answer: 'Zależy od chaty, szczegółowych informacji udzieli szef chaty. Doświadczenie uczy, że pralkę można skutecznie zastąpić miską, a żelazko się raczej nie przyda...'
        },
        {
          id: '2',
          content: 'Czy jest gdzie suszyć przemoczone rzeczy?',
          answer: 'W każdej chacie na pewno się takie miejsce znajdzie! Warto jednak zabrać ze sobą kawałek sznurka do rozwieszenia ciuchów.'
        }
      ]
    },
    {
      id: '4',
      name: 'Inne pytania',
      questions: [
        {
          id: '2',
          content: 'Jaka będzie pogoda?',
          answer: 'Jedynie Pan Bóg raczy wiedzieć...'
        },
        {
          id: '2',
          content: 'Jak daleko jest do basenów termalnych?',
          answer: 'Około 8 km do Szaflar oraz ok. 30km na Słowację (w jedną stronę).'
        },
        {
          id: '2',
          content: 'Czy trzeba na obóz wziąć ciepłą odzież?',
          answer: 'Bezwzględnie TAK! Zerknij do działu "pakowanie". W Tatrach warunki pogodowe zmieniają się bardzo gwałtownie, może nawet spaść śnieg. A w zimie krótki rękawek to raczej nie jest trafiony pomysł.'
        },
        {
          id: '2',
          content: 'Czy warto brać instrumenty muzyczne?',
          answer: 'Nawet jak nie umiesz wydobywać z nich dźwięków - zabierz :) Na pewno znajdzie się ktoś, kto potrafi!'
        },
        {
          id: '2',
          content: ' Czy trzeba chodzić codziennie w góry?',
          answer: 'Nie. Wycieczki w góry są organizowane prawie codziennie. Można się na nie zapisać, ale nie trzeba. Organizowane trasy są zróżnicowane poziomem trudności.'
        }
      ]
    }
  ];

  constructor() {
  }

  getCategories() {
    return this.categories.slice();
  }
}

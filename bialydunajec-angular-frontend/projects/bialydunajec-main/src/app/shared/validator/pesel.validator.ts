import {FormControl} from '@angular/forms';
import {Gender} from '../model/gender.enum';

export function peselValidator(control: FormControl) {
  if (!control.value) {
    return null;
  }
  const isValid = getInfoFromPesel(control.value).valid;
  return isValid ? null : {'invalidPesel': control.value};
}

// TODO: Check pesel function from:
function getInfoFromPesel(pesel): { valid: boolean, gender: Gender, birthDate: Date } {
  if (pesel === null || pesel.length !== 11) {
    return {
      valid: false,
      gender: null,
      birthDate: null
    };
  }

  // pobranie daty
  let rok = parseInt(pesel.substring(0, 2), 10);
  let miesiac = parseInt(pesel.substring(2, 4), 10) - 1;
  let dzien = parseInt(pesel.substring(4, 6), 10);

  if (miesiac > 80) {
    rok += 1800;
    miesiac = miesiac - 80;
  } else if (miesiac >= 60) {
    rok += 2200;
    miesiac = miesiac - 60;
  } else if (miesiac >= 40) {
    rok += 2100;
    miesiac = miesiac - 40;
  } else if (miesiac >= 20) {
    rok += 2000;
    miesiac = miesiac - 20;
  } else {
    rok += 1900;
  }

  const dataUrodzenia = new Date();
  dataUrodzenia.setFullYear(rok, miesiac, dzien);

  // Weryfikacja numeru PESEL
  const wagi = [9, 7, 3, 1, 9, 7, 3, 1, 9, 7];
  let suma = 0;

  for (let i = 0; i < wagi.length; i++) {
    suma += (parseInt(pesel.substring(i, i + 1), 10) * wagi[i]);
  }

  suma = suma % 10;

  const cyfraKontr = parseInt(pesel.substring(10, 11), 10);
  const poprawnosc = (suma === cyfraKontr);

  // określenie płci
  let plec: Gender = null;

  if (parseInt(pesel.substring(9, 10), 10) % 2 === 1) {
    plec = Gender.MALE;
  } else {
    plec = Gender.FEMALE;
  }

  return {
    valid: poprawnosc,
    gender: plec,
    birthDate: dataUrodzenia
  };
}

/*
export function genderWithPeselValidator(gender: Gender, pesel: string) {

}*/

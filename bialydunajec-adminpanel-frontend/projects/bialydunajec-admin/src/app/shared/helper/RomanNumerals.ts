export class RomanNumerals {
  static numberStringToRoman(num: string): string {
    return RomanNumerals.numberToRoman(+num);
  }

  static numberToRoman(num: number): string {
    const romanArray: string[] = [];
    const digits: string[] = num.toString().split('').reverse();
    const digiLeng = digits.length;
    const romanDigitSet = [
      {ones: 'I', fives: 'V', tens: 'X'},
      {ones: 'X', fives: 'L', tens: 'C'},
      {ones: 'C', fives: 'D', tens: 'M'},
      {ones: 'M', fives: '', tens: ''}
    ];
    for (let i = 0; i < digiLeng; i++) {
      const n = parseInt(digits[i], 10);
      romanArray.unshift(translator(n, romanDigitSet[i]));
    }
    return romanArray.join('');
    // Your code here
  }


  static romanToNumber(str1: string): number | null {
    if (str1 == null) {
      return null;
    }
    let num = RomanNumerals.romanCharToNumber(str1.charAt(0));
    let pre, curr;

    for (let i = 1; i < str1.length; i++) {
      curr = RomanNumerals.romanCharToNumber(str1.charAt(i));
      pre = RomanNumerals.romanCharToNumber(str1.charAt(i - 1));
      if (curr <= pre) {
        num += curr;
      } else {
        num = num - pre * 2 + curr;
      }
    }

    return num;
  }

  private static romanCharToNumber(c) {
    switch (c) {
      case 'I':
        return 1;
      case 'V':
        return 5;
      case 'X':
        return 10;
      case 'L':
        return 50;
      case 'C':
        return 100;
      case 'D':
        return 500;
      case 'M':
        return 1000;
      default:
        return -1;
    }
  }
}

function translator(n: number, set: { ones: string, fives: string, tens: string }): string {
  const ones = set.ones,
    fives = set.fives,
    tens = set.tens;
  if (n == 0) {
    return '';
  } else if (n < 4) {
    return Array(n).fill(ones).join('');
  } else if (n == 4) {
    return ones.concat(fives);
  } else if (n < 9) {
    return fives.concat(Array(n - 5).fill(ones).join(''));
  } else if (n == 9) {
    return ones.concat(tens);
  }
  return '';
}


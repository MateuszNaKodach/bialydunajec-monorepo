export class CampEditionShirtDto {
  campEditionShirtId: string;
  campRegistrationsEditionId: string;
  shirtSizesFileUrl?: string;
  colorOptions: ShirtColorOptionDto[];
  sizeOptions: ShirtSizeOptionDto[];
}

export class ShirtColorOptionDto {
  shirtColorOptionId: string;
  color: ColorDto;
  available: boolean;
}

export class ColorDto {
  name: string;
  hexValue?: string;


  constructor(name: string, hexValue: string) {
    this.name = name;
    this.hexValue = hexValue;
  }
}

export class ShirtSizeOptionDto {
  shirtSizeOptionId: string;
  size: ShirtSizeDto;
  available: boolean;
}

export class ShirtSizeDto {
  name: string;
  type: 'MALE' | 'FEMALE';
  height?: number;
  width?: number;
  length?: number;


  constructor(name: string, type: "MALE" | "FEMALE", height: number, width: number, length: number) {
    this.name = name;
    this.type = type;
    this.height = height;
    this.width = width;
    this.length = length;
  }
}

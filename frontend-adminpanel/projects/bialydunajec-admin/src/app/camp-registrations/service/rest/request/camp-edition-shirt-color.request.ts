import {ColorDto} from '../dto/camp-edition-shirt.dto';

export class CampEditionShirtColorRequest {
  color: ColorDto;
  available: boolean;

  constructor(color: ColorDto, available: boolean) {
    this.color = color;
    this.available = available;
  }
}

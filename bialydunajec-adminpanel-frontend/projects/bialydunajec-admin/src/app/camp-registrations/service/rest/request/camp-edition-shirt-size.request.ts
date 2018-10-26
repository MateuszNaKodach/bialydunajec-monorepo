import {ShirtSizeDto} from '../dto/camp-edition-shirt.dto';

export class CampEditionShirtSizeRequest {
  size: ShirtSizeDto;
  available: boolean;


  constructor(size: ShirtSizeDto, available: boolean) {
    this.size = size;
    this.available = available;
  }
}

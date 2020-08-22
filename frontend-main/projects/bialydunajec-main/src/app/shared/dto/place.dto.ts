import {AddressDto} from './address.dto';
import {GeoLocationDto} from './geo-location.dto';

export class PlaceDto {
  address: AddressDto;
  geoLocation?: GeoLocationDto;


  constructor(address: AddressDto, geoLocation: GeoLocationDto) {
    this.address = address;
    this.geoLocation = geoLocation;
  }
}

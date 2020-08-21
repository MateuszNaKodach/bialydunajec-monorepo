export class GeoLocationDto {
  latitude: number;
  longitude: number;


  constructor(latitude: number, longitude: number) {
    this.latitude = latitude;
    this.longitude = longitude;
  }
}

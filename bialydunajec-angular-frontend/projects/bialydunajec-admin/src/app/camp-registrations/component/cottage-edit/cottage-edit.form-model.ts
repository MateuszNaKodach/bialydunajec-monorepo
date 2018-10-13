import {PlaceDto} from '../../../shared/service/rest/dto/place.dto';
import {CottageSpaceDto} from '../../service/rest/response/dto/cottage-space.dto';
import {CampersLimitationsDto} from '../../service/rest/response/dto/camper-limitations.dto';
import {BankTransferDetailsDto} from '../../service/rest/response/dto/bank-transfer-details.dto';
import {CottageResponse} from '../../service/rest/response/cottage.response';

export class CottageEditFormModel {
  cottageType: string;
  academicMinistryId: string;
  name: string;
  logoImageUrl?: string;
  buildingPhotoUrl?: string;
  place?: PlaceDto;
  cottageSpace?: CottageSpaceDto;
  campersLimitations?: CampersLimitationsDto;
  bankTransferDetails?: BankTransferDetailsDto;


  constructor(cottageType: string, academicMinistryId: string, name: string, logoImageUrl: string, buildingPhotoUrl: string, place: PlaceDto, cottageSpace: CottageSpaceDto, campersLimitations: CampersLimitationsDto, bankTransferDetails: BankTransferDetailsDto) {
    this.cottageType = cottageType;
    this.academicMinistryId = academicMinistryId;
    this.name = name;
    this.logoImageUrl = logoImageUrl;
    this.buildingPhotoUrl = buildingPhotoUrl;
    this.place = place;
    this.cottageSpace = cottageSpace == null ? new CottageSpaceDto() : cottageSpace;
    this.campersLimitations = campersLimitations;
    this.bankTransferDetails = bankTransferDetails == null ? new BankTransferDetailsDto() : bankTransferDetails;
  }

  static fromDto(dto: CottageResponse) {
    return new CottageEditFormModel(
      dto.cottageType,
      dto.academicMinistryId,
      dto.name,
      dto.logoImageUrl,
      dto.buildingPhotoUrl,
      {
        address: {
          street: dto.place && dto.place.address ? dto.place.address.street : null,
          homeNumber: dto.place && dto.place.address ? dto.place.address.homeNumber : null,
          city: dto.place && dto.place.address ? dto.place.address.city : null,
          postalCode: dto.place && dto.place.address ? dto.place.address.postalCode : null
        },
        geoLocation: {
          latitude: dto.place && dto.place.geoLocation ? dto.place.geoLocation.latitude : null,
          longitude: dto.place && dto.place.geoLocation ? dto.place.geoLocation.longitude : null
        }
      },
      dto.cottageSpace,
      {
        ageRange: {
          min: dto.campersLimitations && dto.campersLimitations.ageRange ? dto.campersLimitations.ageRange.min : null,
          max: dto.campersLimitations && dto.campersLimitations.ageRange ? dto.campersLimitations.ageRange.min : null
        }
      },
      dto.bankTransferDetails
    );
  }
}

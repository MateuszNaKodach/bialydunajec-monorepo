import {PlaceDto} from '../../../../shared/dto/place.dto';
import {CottageSpaceDto} from '../../../../campers-registration/service/rest/dto/cottage-space.dto';
import {CampersLimitationsDto} from '../../../../campers-registration/service/rest/dto/camper-limitations.dto';
import {BankTransferDetailsDto} from '../../../../campers-registration/service/rest/dto/bank-transfer-details.dto';
import {CottageResponse} from '../../../../campers-registration/service/rest/response/cottage.response';
import {ExtendedDescriptionDto} from '../../../../shared/dto/extended-description.dto';

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
  cottageBoss?: CottageBossDto;

  constructor(
    cottageType: string,
    academicMinistryId: string,
    name: string,
    logoImageUrl: string,
    buildingPhotoUrl: string,
    place: PlaceDto,
    cottageSpace: CottageSpaceDto,
    campersLimitations: CampersLimitationsDto,
    bankTransferDetails: BankTransferDetailsDto,
    cottageBoss: CottageBossDto = null) {
    this.cottageType = cottageType;
    this.academicMinistryId = academicMinistryId;
    this.name = name;
    this.logoImageUrl = logoImageUrl;
    this.buildingPhotoUrl = buildingPhotoUrl;
    this.place = place;
    this.cottageSpace = cottageSpace == null ? new CottageSpaceDto() : cottageSpace;
    this.campersLimitations = campersLimitations;
    this.bankTransferDetails = bankTransferDetails == null ? new BankTransferDetailsDto() : bankTransferDetails;
    this.cottageBoss = cottageBoss == null ? new CottageBossDto() : cottageBoss;
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
      dto.bankTransferDetails,
      dto.cottageBoss
    );
  }
}

export class CottageBossDto {
  firstName: string;
  lastName: string;
  phoneNumber: string;
  emailAddress: string;
  university: string;
  fieldOfStudy: string;
  photoUrl: string;
  personalDescription: ExtendedDescriptionDto;


  constructor(firstName: string = null, lastName: string = null, phoneNumber: string = null, emailAddress: string = null, university: string = null, fieldOfStudy: string = null, photoUrl: string = null, personalDescription: ExtendedDescriptionDto = null) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    this.emailAddress = emailAddress;
    this.university = university;
    this.fieldOfStudy = fieldOfStudy;
    this.photoUrl = photoUrl;
    this.personalDescription = personalDescription == null ? new ExtendedDescriptionDto() : personalDescription;
  }
}

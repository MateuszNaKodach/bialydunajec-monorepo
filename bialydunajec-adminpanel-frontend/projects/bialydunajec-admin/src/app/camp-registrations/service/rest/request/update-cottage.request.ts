import {PlaceDto} from '../../../../shared/service/rest/dto/place.dto';
import {CottageSpaceDto} from '../dto/cottage-space.dto';
import {CampersLimitationsDto} from '../dto/camper-limitations.dto';
import {BankTransferDetailsDto} from '../dto/bank-transfer-details.dto';

export class UpdateCottageRequest {
  campRegistrationsEditionId: string;
  cottageType: string;
  academicMinistryId?: string;
  name: string;
  logoImageUrl?: string;
  buildingPhotoUrl?: string;
  place?: PlaceDto;
  cottageSpace: CottageSpaceDto;
  campersLimitations?: CampersLimitationsDto;
  bankTransferDetails?: BankTransferDetailsDto;
  cottageState: string;

  constructor(campRegistrationsEditionId: string, cottageType: string, academicMinistryId: string, name: string, logoImageUrl: string, buildingPhotoUrl: string, place: PlaceDto, cottageSpace: CottageSpaceDto, campersLimitations: CampersLimitationsDto, bankTransferDetails: BankTransferDetailsDto, cottageState: string) {
    this.campRegistrationsEditionId = campRegistrationsEditionId;
    this.cottageType = cottageType;
    this.academicMinistryId = academicMinistryId;
    this.name = name;
    this.logoImageUrl = logoImageUrl;
    this.buildingPhotoUrl = buildingPhotoUrl;
    this.place = place;
    this.cottageSpace = cottageSpace;
    this.campersLimitations = campersLimitations;
    this.bankTransferDetails = bankTransferDetails;
    this.cottageState = cottageState;
  }
}

import {PlaceDto} from '../../../../shared/service/rest/dto/place.dto';
import {CottageSpaceDto} from '../dto/cottage-space.dto';
import {CampersLimitationsDto} from '../dto/camper-limitations.dto';
import {BankTransferDetailsDto} from '../dto/bank-transfer-details.dto';

export class CottageResponse {
  cottageId: string;
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
}


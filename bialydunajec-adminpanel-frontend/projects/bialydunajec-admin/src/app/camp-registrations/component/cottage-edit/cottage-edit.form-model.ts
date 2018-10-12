import {PlaceDto} from '../../../shared/service/rest/dto/place.dto';
import {CottageSpaceDto} from '../../service/rest/response/dto/cottage-space.dto';
import {CampersLimitationsDto} from '../../service/rest/response/dto/camper-limitations.dto';
import {BankTransferDetailsDto} from '../../service/rest/response/dto/bank-transfer-details.dto';

export class CottageEditFormModel {
  cottageType: 'STANDALONE' | 'ACADEMIC_MINISTRY';
  academicMinistryId: string;
  name: string;
  logoImageUrl?: string;
  buildingPhotoUrl?: string;
  place?: PlaceDto;
  cottageSpace?: CottageSpaceDto;
  campersLimitations?: CampersLimitationsDto;
  bankTransferDetails?: BankTransferDetailsDto;
}

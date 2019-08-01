import {PlaceDto} from '../../../../shared/dto/place.dto';
import {CottageBossDto} from './cottage-edit.form-model';
import {AuditDto} from '../../../../shared/dto/audit.dto';

export class CottageInfoDto {
  cottageId: string;
  campRegistrationsEditionId: string;
  cottageType: string;
  academicMinistryId?: string;
  name: string;
  logoImageUrl?: string;
  buildingPhotoUrl?: string;
  place?: PlaceDto;
  cottageState: string;
  cottageBoss: CottageBossDto;
  audit: AuditDto;
}

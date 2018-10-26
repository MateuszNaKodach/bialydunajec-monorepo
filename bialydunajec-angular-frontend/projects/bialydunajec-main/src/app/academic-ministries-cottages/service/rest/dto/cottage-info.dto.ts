import {PlaceDto} from '../../../../../../../bialydunajec-admin/src/app/shared/service/rest/dto/place.dto';
import {CottageBossDto} from '../../../../../../../bialydunajec-admin/src/app/camp-registrations/component/cottage-edit/cottage-edit.form-model';
import {AuditDto} from '../../../../../../../bialydunajec-commons/src/lib/rest/shared-kernel/audit.dto';

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

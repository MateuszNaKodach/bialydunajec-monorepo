import {PlaceDto} from '../../../../../../../bialydunajec-admin/src/app/shared/service/rest/dto/place.dto';

export class CampRegistrationsCottageResponse {
  cottageId: string;
  cottageType: string;
  academicMinistryId?: string;
  name: string;
  logoImageUrl?: string;
  buildingPhotoUrl?: string;
  place?: PlaceDto;
  hasSpace: boolean;
}

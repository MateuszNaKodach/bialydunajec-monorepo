import {PlaceDto} from '../../../../shared/dto/place.dto';

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

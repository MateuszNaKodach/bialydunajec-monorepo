import {PersonalTitleDto} from './personal-title.dto';
import {ExtendedDescriptionDto} from '../../../../shared/dto/extended-description.dto';

export class AcademicPriestDto {
  academicPriestId: string;
  firstName: string;
  lastName: string;
  personalTitle?: PersonalTitleDto;
  emailAddress?: string;
  phoneNumber?: string;
  description?: ExtendedDescriptionDto;
  photoUrl?: string;
}

import {CampRegistrationsResponse} from './camp-registrations.response';

export class CampRegistrationsEditionResponse {
  campRegistrationsEditionId: number;
  editionStartDate: Date;
  editionEndDate: Date;
  campRegistrations: CampRegistrationsResponse;
}


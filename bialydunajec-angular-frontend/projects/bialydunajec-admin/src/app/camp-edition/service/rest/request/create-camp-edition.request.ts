export class CreateCampEditionRequest {
  campEditionId: number;
  campEditionStartDate: Date;
  campEditionEndDate: Date;

  constructor(campEditionId: number, campEditionStartDate: Date, campEditionEndDate: Date) {
    this.campEditionId = campEditionId;
    this.campEditionStartDate = campEditionStartDate;
    this.campEditionEndDate = campEditionEndDate;
  }
}

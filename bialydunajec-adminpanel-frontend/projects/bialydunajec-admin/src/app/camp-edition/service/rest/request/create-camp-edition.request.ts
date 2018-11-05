export class CreateCampEditionRequest {
  campEditionId: number;
  campEditionStartDate: Date;
  campEditionEndDate: Date;
  campEditionPrice: number;


  constructor(campEditionId: number, campEditionStartDate: Date, campEditionEndDate: Date, campEditionPrice: number) {
    this.campEditionId = campEditionId;
    this.campEditionStartDate = campEditionStartDate;
    this.campEditionEndDate = campEditionEndDate;
    this.campEditionPrice = campEditionPrice;
  }
}

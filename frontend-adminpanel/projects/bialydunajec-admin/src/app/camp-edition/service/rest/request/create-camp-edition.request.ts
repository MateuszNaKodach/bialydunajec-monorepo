export class CreateCampEditionRequest {
  campEditionId: number;
  campEditionStartDate: Date;
  campEditionEndDate: Date;
  campEditionPrice: number;
  campEditionDownPaymentAmount?: number;

  constructor(campEditionId: number, campEditionStartDate: Date, campEditionEndDate: Date, campEditionPrice: number, campEditionDownPaymentAmount: number) {
    this.campEditionId = campEditionId;
    this.campEditionStartDate = campEditionStartDate;
    this.campEditionEndDate = campEditionEndDate;
    this.campEditionPrice = campEditionPrice;
    this.campEditionDownPaymentAmount = campEditionDownPaymentAmount;
  }
}

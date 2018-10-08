export class CampRegistrationsResponse {
  campRegistrationsId: number;
  status: String;
  timerStartDate: Date | null;
  timerEndDate: Date | null;
  lastStartedAt: Date | null;
  lastSuspendAt: Date | null;
  lastUnsuspendAt: Date | null;
  lastFinishedAt: Date | null;
}

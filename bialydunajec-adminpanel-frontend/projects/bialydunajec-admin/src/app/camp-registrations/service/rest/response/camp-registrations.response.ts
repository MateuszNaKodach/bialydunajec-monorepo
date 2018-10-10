export class CampRegistrationsResponse {
  campRegistrationsId: number;
  status: RegistrationsStatus;
  timerStartDate: Date | null;
  timerEndDate: Date | null;
  lastStartedAt: Date | null;
  lastSuspendAt: Date | null;
  lastUnsuspendAt: Date | null;
  lastFinishedAt: Date | null;
}

export enum RegistrationsStatus {
  /**
   * Lack of necessary configuration
   */
  UNCONFIGURED_TIMER,
  /**
   * All necessary configuration is setup
   */
  CONFIGURED_TIMER,
  /**
   * Registrations startDate reached or activated manually
   */
  IN_PROGRESS,
  /**
   * Deactivated - startDate doesn't matter
   */
  SUSPENDED,
  /**
   * Registrations are FINISHED when endDate is reached
   */
  FINISHED
}

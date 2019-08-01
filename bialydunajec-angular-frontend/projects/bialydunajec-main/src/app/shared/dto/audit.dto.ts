export class AuditDto {
  createdDate: Date;
  createdBy?: AuditorDto;
  lastModifiedDate?: Date;
  lastModifiedBy?: AuditorDto;
}

export class AuditorDto {
  auditorId: string;
  firstName: string;
  lastName: string;
  emailAddress: string;
}

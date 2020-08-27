package org.bialydunajec.ddd.dto.sharedkernel

import java.time.ZonedDateTime

data class AuditDto (
        val createdDate: ZonedDateTime,
        val createdBy: AuditorDto?,
        val lastModifiedDate: ZonedDateTime?,
        val lastModifiedBy: AuditorDto?
)

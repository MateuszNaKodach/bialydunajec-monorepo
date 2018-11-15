package org.bialydunajec.ddd.application.base.dto

import java.time.ZonedDateTime

data class AuditDto (
        val createdDate: ZonedDateTime,
        val createdBy: AuditorDto?,
        val lastModifiedDate: ZonedDateTime?,
        val lastModifiedBy: AuditorDto?
)
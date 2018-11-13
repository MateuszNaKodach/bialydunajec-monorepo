package org.bialydunajec.ddd.application.base.dto

import java.time.Instant

data class AuditDto (
        val createdDate: Instant,
        val createdBy: AuditorDto?,
        val lastModifiedDate: Instant?,
        val lastModifiedBy: AuditorDto?
)
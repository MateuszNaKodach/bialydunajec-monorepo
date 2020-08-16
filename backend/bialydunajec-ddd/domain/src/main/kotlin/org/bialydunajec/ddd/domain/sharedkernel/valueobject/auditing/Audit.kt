package org.bialydunajec.ddd.domain.sharedkernel.valueobject.auditing

import java.time.Instant

data class Audit (
        val createdDate: Instant,
        val createdBy: Auditor?,
        val lastModifiedDate: Instant?,
        val lastModifiedBy: Auditor?
)
package org.bialydunajec.ddd.domain.sharedkernel.valueobject.auditing

import org.bialydunajec.ddd.domain.base.valueobject.DbAggregateId
import org.bialydunajec.ddd.domain.base.valueobject.ValueObject
import javax.persistence.Embeddable
import javax.persistence.Embedded
import javax.validation.constraints.NotNull

@Embeddable
data class Auditor(
        @Embedded
        @NotNull
        val auditorId: DbAggregateId?
) : ValueObject {
    constructor(audtiorId: String) : this(DbAggregateId(audtiorId))
}

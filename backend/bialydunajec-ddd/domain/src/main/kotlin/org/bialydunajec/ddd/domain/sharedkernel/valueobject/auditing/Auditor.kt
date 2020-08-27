package org.bialydunajec.ddd.domain.sharedkernel.valueobject.auditing

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.AggregateId
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.ValueObject
import javax.persistence.Embeddable
import javax.persistence.Embedded
import javax.validation.constraints.NotNull

@Embeddable
data class Auditor(
        @Embedded
        @NotNull
        val auditorId: AggregateId?
) : ValueObject {
    constructor(audtiorId: String) : this(AggregateId(audtiorId))
}

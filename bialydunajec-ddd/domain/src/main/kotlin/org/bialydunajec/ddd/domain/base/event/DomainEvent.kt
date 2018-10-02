package org.bialydunajec.ddd.domain.base.event

import org.bialydunajec.ddd.domain.base.valueobject.DomainEventId
import org.bialydunajec.ddd.domain.base.valueobject.Identifier
import java.io.Serializable
import java.time.Instant

interface DomainEvent<AggregateIdType : Identifier<*>> : Serializable {
    val aggregateId: AggregateIdType
    val domainEventId: DomainEventId
        get() = DomainEventId()
    val occurredAt: Instant
        get() = Instant.now()
    //val aggregateVersion: Int
}

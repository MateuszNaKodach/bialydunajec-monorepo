package org.bialydunajec.ddd.domain.event

import org.bialydunajec.ddd.domain.valueobject.AggregateId
import org.bialydunajec.ddd.domain.valueobject.DomainEventId
import java.io.Serializable
import java.time.Instant

interface DomainEvent<AggregateIdType : AggregateId> : Serializable {
    val aggregateId: AggregateIdType
    val domainEventId: DomainEventId
        get() = DomainEventId()
    val occurredAt: Instant
        get() = Instant.now()
    //val aggregateVersion: Int
}

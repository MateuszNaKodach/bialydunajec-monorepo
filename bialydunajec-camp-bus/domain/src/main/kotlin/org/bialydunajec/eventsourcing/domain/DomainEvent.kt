package org.bialydunajec.eventsourcing.domain

import java.time.Instant

abstract class DomainEvent<AggregateIdType : AggregateId> {
    abstract val aggregateId: AggregateIdType
    abstract val aggregateVersion: AggregateVersion
    abstract val domainEventId: DomainEventId
    abstract val occurredAt: Instant
    abstract val aggregateType: Class<*>
    abstract val eventStreamType: Class<*>

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DomainEvent<*>

        if (domainEventId != other.domainEventId) return false

        return true
    }

    override fun hashCode(): Int {
        return domainEventId.hashCode()
    }


}

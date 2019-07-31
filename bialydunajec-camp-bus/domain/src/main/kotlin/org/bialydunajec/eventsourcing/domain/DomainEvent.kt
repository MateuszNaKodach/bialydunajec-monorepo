package org.bialydunajec.eventsourcing.domain

import java.time.Instant

abstract class DomainEvent<AggregateIdType : AggregateId>(
        val aggregateId: AggregateIdType,
        val aggregateVersion: AggregateVersion,
        val domainEventId: DomainEventId,
        val occurredAt: Instant,
        val aggregateType: Class<*>,
        val eventStreamType: Class<*>
) {

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

    //TODO: Consider create wrapper for message in command handler with metadata
    // Message<Command>
    // event execute command
    // Message<Event> from event
    //Store Message<Event> w ids
    //abstract fun causedBy(domainMessage: DomainMessage): DomainEventType


}

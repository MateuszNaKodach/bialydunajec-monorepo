package org.bialydunajec.eventsourcing.infrastructure.eventstore

import org.bialydunajec.eventsourcing.domain.AggregateId
import org.bialydunajec.eventsourcing.domain.DomainEvent

interface EventStore {

    fun saveAll(events: List<DomainEvent<*>>)

    fun <EventType : DomainEvent<*>> loadEvents(payloadClass: Class<EventType>, aggregateId: AggregateId): List<DomainEvent<*>>
}

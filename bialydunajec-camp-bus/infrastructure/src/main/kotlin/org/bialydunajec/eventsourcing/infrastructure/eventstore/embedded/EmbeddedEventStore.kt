package org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded

import org.bialydunajec.eventsourcing.domain.AggregateId
import org.bialydunajec.eventsourcing.domain.DomainEvent
import org.bialydunajec.eventsourcing.infrastructure.eventstore.EventStore
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.EventStorageEngine

internal class EmbeddedEventStore(private val storageEngine: EventStorageEngine) : EventStore {

    override fun saveAll(events: List<DomainEvent<*>>) =
            storageEngine.appendEvents(events)

    override fun <EventType : DomainEvent<*>> loadEvents(payloadClass: Class<EventType>, aggregateId: AggregateId): List<DomainEvent<*>> =
            storageEngine.readEvents(payloadClass, aggregateId)

}

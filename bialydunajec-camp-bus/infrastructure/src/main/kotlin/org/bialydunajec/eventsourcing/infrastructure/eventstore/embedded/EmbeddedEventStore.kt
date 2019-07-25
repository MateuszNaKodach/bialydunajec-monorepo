package org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded

import org.bialydunajec.eventsourcing.domain.AggregateId
import org.bialydunajec.eventsourcing.domain.AggregateVersion
import org.bialydunajec.eventsourcing.domain.DomainEvent
import org.bialydunajec.eventsourcing.domain.TimeProvider
import org.bialydunajec.eventsourcing.infrastructure.eventstore.EventStore
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.EventStorageEngine
import java.time.Instant

internal class EmbeddedEventStore(
        private val storageEngine: EventStorageEngine,
        private val timeProvider: TimeProvider
) : EventStore {

    override fun publish(event: DomainEvent<*>) =
            storageEngine.appendDomainEvent(event)

    override fun <EventType : DomainEvent<*>> readEvents(payloadClass: Class<EventType>, aggregateId: AggregateId): List<EventType> =
            storageEngine.readEvents(payloadClass, aggregateId, timeProvider())

    override fun <EventType : DomainEvent<*>> readEvents(payloadClass: Class<EventType>, aggregateId: AggregateId, toVersion: AggregateVersion): List<EventType> =
            storageEngine.readEvents(payloadClass, aggregateId, timeProvider(), toVersion)

    override fun <EventType : DomainEvent<*>> readEvents(payloadClass: Class<EventType>, aggregateId: AggregateId, toEventTimestamp: Instant): List<EventType> =
            storageEngine.readEvents(payloadClass, aggregateId, toEventTimestamp)
}

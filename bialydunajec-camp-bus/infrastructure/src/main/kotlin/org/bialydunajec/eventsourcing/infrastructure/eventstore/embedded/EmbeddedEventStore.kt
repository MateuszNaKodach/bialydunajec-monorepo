package org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded

import org.bialydunajec.eventsourcing.domain.AggregateId
import org.bialydunajec.eventsourcing.domain.DomainEvent
import org.bialydunajec.eventsourcing.domain.TimeProvider
import org.bialydunajec.eventsourcing.infrastructure.eventstore.EventStore
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.EventStorageEngine
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.EventStreamName
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.ExpectedEventStreamVersion
import java.time.Instant

internal class EmbeddedEventStore(
        private val storageEngine: EventStorageEngine,
        private val timeProvider: TimeProvider
) : EventStore {

    override fun publish(event: DomainEvent<*>) =
            storageEngine.appendDomainEvent(EventStreamName(event.aggregateId.toString()), event, ExpectedEventStreamVersion.Any)

    override fun <EventType : DomainEvent<*>> readEvents(payloadClass: Class<EventType>, aggregateId: AggregateId): List<EventType> =
            storageEngine.readEvents(payloadClass, aggregateId, timeProvider())

    override fun <EventType : DomainEvent<*>> readEvents(payloadClass: Class<EventType>, aggregateId: AggregateId, toTimestamp: Instant): List<EventType> =
            storageEngine.readEvents(payloadClass, aggregateId, toTimestamp)
}

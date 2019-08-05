package org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded

import org.bialydunajec.eventsourcing.domain.AggregateId
import org.bialydunajec.eventsourcing.domain.DomainEvent
import org.bialydunajec.eventsourcing.domain.TimeProvider
import org.bialydunajec.eventsourcing.infrastructure.eventstore.EventStore
import org.bialydunajec.eventsourcing.infrastructure.eventstore.EventStream
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.EventStorageEngine
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.EventStreamName
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.EventStreamVersion
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.ExpectedEventStreamVersion
import java.time.Instant

internal class EmbeddedEventStore(
        private val storageEngine: EventStorageEngine,
        private val timeProvider: TimeProvider
) : EventStore {

    override fun publish(event: DomainEvent<*>) =
            storageEngine.appendDomainEvent(EventStreamName(event.aggregateId.toString()), event, ExpectedEventStreamVersion.Any)

    override fun <EventType : DomainEvent<*>> readEvents(payloadClass: Class<EventType>, aggregateId: AggregateId): EventStream<EventType> =
            loadEventStream(payloadClass, aggregateId, timeProvider())

    override fun <EventType : DomainEvent<*>> readEvents(payloadClass: Class<EventType>, aggregateId: AggregateId, toTimestamp: Instant): EventStream<EventType> =
            loadEventStream(payloadClass, aggregateId, toTimestamp)

    private fun <EventType : DomainEvent<*>> loadEventStream(payloadClass: Class<EventType>, aggregateId: AggregateId, toTimestamp: Instant): EventStream<EventType> {
        return storageEngine.readEvents(payloadClass, aggregateId, toTimestamp)
                .let {
                    EventStream(EventStreamName(aggregateId.toString()), EventStreamVersion.Exactly(it.size - 1), it)
                }
    }
}

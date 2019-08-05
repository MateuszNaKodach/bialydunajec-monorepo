package org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine

import org.bialydunajec.eventsourcing.domain.AggregateId
import org.bialydunajec.eventsourcing.domain.DomainEvent
import org.bialydunajec.eventsourcing.infrastructure.eventstore.EventSerializer
import java.time.Instant
import kotlin.reflect.KClass

internal interface EventStorageEngine {

    val eventSerializer: EventSerializer

    fun appendDomainEvent(streamName: EventStreamName, domainEvent: DomainEvent<*>, expectedVersion: ExpectedEventStreamVersion)

    fun <EventType : DomainEvent<*>> readEvents(domainEventType: KClass<EventType>, aggregateId: AggregateId, toEventTimestamp: Instant): List<EventType> =
            readEvents(domainEventType.java, aggregateId, toEventTimestamp)

    fun <EventType : DomainEvent<*>> readEvents(domainEventType: Class<EventType>, aggregateId: AggregateId, toEventTimestamp: Instant): List<EventType>
}

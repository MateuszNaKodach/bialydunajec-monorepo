package org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.inmemory

import arrow.mtl.instances.list.functorFilter.filter
import org.bialydunajec.eventsourcing.domain.AggregateId
import org.bialydunajec.eventsourcing.domain.DomainEvent
import org.bialydunajec.eventsourcing.infrastructure.eventstore.EventSerializer
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.*
import org.bialydunajec.eventsourcing.infrastructure.eventstore.exception.DomainEventAlreadyStoredException
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap


internal class InMemoryEventStorageEngine(eventSerializer: EventSerializer) : AbstractEventStorageEngine(eventSerializer) {

    private val eventStreams = ConcurrentHashMap<EventStreamName, List<StoreDomainEventEntry>>()

    override fun storeEvent(streamName: EventStreamName, domainEvent: StoreDomainEventEntry, expectedVersion: ExpectedEventStreamVersion) {
        val events = getEventsBy(streamName)
        assertIfEventStreamVersionIsAsExpected(events, expectedVersion)
        assertIfDomainEventNotAlreadyStored(events, domainEvent)
        events.let {
            eventStreams[streamName] = it.plus(domainEvent)
        }
    }

    private fun getEventsBy(streamName: EventStreamName) = eventStreams[streamName] ?: emptyList()

    private fun assertIfEventStreamVersionIsAsExpected(events: List<StoreDomainEventEntry>, expectedVersion: ExpectedEventStreamVersion) {
        val actualVersion = if (events.isEmpty()) {
            EventStreamVersion.None
        } else {
            EventStreamVersion.Exactly(events.size - 1)
        }
        val isVersionAsExpected = when (expectedVersion) {
            is ExpectedEventStreamVersion.None -> actualVersion == EventStreamVersion.None
            is ExpectedEventStreamVersion.Exactly -> actualVersion == EventStreamVersion.Exactly(events.size - 1)
            is ExpectedEventStreamVersion.Any -> true
        }
        if (!isVersionAsExpected) {
            throw EventStreamVersionIsNotAsExpected(expectedVersion, actualVersion)
        }
    }

    private fun assertIfDomainEventNotAlreadyStored(storedEvents: List<StoreDomainEventEntry>, newEvent: StoreDomainEventEntry) {
        if (storedEvents.contains(newEvent)) {
            throw DomainEventAlreadyStoredException(newEvent.eventIdentifier)
        }
    }

    override fun <EventType : DomainEvent<*>> readEvents(
            domainEventType: Class<EventType>,
            aggregateId: AggregateId,
            toEventTimestamp: Instant): List<EventType> =
            getEventsBy(EventStreamName(aggregateId.toString()))
                    .filter { !it.timestamp.isAfter(toEventTimestamp) }
                    .map { extractDomainEvent(domainEventType, it) }
}


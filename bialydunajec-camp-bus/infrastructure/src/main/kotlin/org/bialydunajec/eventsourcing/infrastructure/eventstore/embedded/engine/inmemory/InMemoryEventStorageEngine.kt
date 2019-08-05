package org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.inmemory

import arrow.mtl.instances.list.functorFilter.filter
import org.bialydunajec.eventsourcing.domain.AggregateId
import org.bialydunajec.eventsourcing.domain.AggregateVersion
import org.bialydunajec.eventsourcing.domain.DomainEvent
import org.bialydunajec.eventsourcing.infrastructure.eventstore.EventSerializer
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.AbstractEventStorageEngine
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.EventStreamVersionIsNotAsExpected
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.ExpectedEventStreamVersion
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.StoreDomainEventEntry
import org.bialydunajec.eventsourcing.infrastructure.eventstore.exception.DomainEventAlreadyStoredException
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap


internal class InMemoryEventStorageEngine(eventSerializer: EventSerializer) : AbstractEventStorageEngine(eventSerializer) {

    private val eventStreams = ConcurrentHashMap<String, List<StoreDomainEventEntry>>()

    override fun storeEvent(domainEvent: StoreDomainEventEntry, expectedVersion: ExpectedEventStreamVersion) {
        val streamName = domainEvent.aggregateIdentifier
        val events = getEventsBy(streamName);
        assertIfEventStreamVersionIsAsExpected(events, expectedVersion)
        assertIfDomainEventNotAlreadyStored(events, domainEvent)
        events.let {
            eventStreams[streamName] = it.plus(domainEvent)
        }
    }

    private fun getEventsBy(streamName: String) = eventStreams[streamName] ?: emptyList<StoreDomainEventEntry>()

    private fun assertIfEventStreamVersionIsAsExpected(events: List<StoreDomainEventEntry>, expectedVersion: ExpectedEventStreamVersion) {
        val isVersionAsExpected = when (expectedVersion) {
            is ExpectedEventStreamVersion.None -> events.isEmpty()
            is ExpectedEventStreamVersion.Exactly -> events.size - 1 == expectedVersion.version
            is ExpectedEventStreamVersion.Any -> true
        }
        if(!isVersionAsExpected){
            throw EventStreamVersionIsNotAsExpected()
        }
    }

    private fun assertIfDomainEventNotAlreadyStored(domainEvent: StoreDomainEventEntry) {
        if (checkIfDomainEventAlreadyStored(domainEvent)) {
            throw DomainEventAlreadyStoredException(domainEvent.eventIdentifier)
        }
    }

    private fun checkIfDomainEventAlreadyStored(domainEvent: StoreDomainEventEntry) =
            eventStreams[domainEvent.aggregateIdentifier]?.any { it.eventIdentifier == domainEvent.eventIdentifier }
                    ?: false


    override fun <EventType : DomainEvent<*>> readEvents(
            domainEventType: Class<EventType>,
            aggregateId: AggregateId,
            toEventTimestamp: Instant,
            toAggregateVersion: AggregateVersion?): List<EventType> =
            getEventsBy(aggregateId.toString())
                    .filter { !it.timestamp.isAfter(toEventTimestamp) }
                    .filter { storedEventVersion ->
                        toAggregateVersion
                                ?.toLong()
                                ?.let { toAggregateVersion -> storedEventVersion.aggregateVersion <= toAggregateVersion }
                                ?: true
                    }
                    .map { extractDomainEvent(domainEventType, it) }
}


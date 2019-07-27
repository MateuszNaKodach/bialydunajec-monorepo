package org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.inmemory

import arrow.mtl.instances.list.functorFilter.filter
import org.bialydunajec.eventsourcing.domain.AggregateId
import org.bialydunajec.eventsourcing.domain.AggregateVersion
import org.bialydunajec.eventsourcing.domain.DomainEvent
import org.bialydunajec.eventsourcing.infrastructure.eventstore.EventSerializer
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.AbstractEventStorageEngine
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.StoreDomainEventEntry
import org.bialydunajec.eventsourcing.infrastructure.eventstore.exception.DomainEventAlreadyStoredException
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap


internal class InMemoryEventStorageEngine(eventSerializer: EventSerializer) : AbstractEventStorageEngine(eventSerializer) {

    private val eventStorage = ConcurrentHashMap<String, List<StoreDomainEventEntry>>()

    override fun storeEvent(domainEvent: StoreDomainEventEntry) {
        assertIfDomainEventNotAlreadyStored(domainEvent)
        (eventStorage[domainEvent.aggregateIdentifier] ?: emptyList<StoreDomainEventEntry>()).let {
            eventStorage[domainEvent.aggregateIdentifier] = it.plus(domainEvent)
        }
    }

    private fun assertIfDomainEventNotAlreadyStored(domainEvent: StoreDomainEventEntry) {
        if (checkIfDomainEventAlreadyStored(domainEvent)) {
            throw DomainEventAlreadyStoredException(domainEvent.eventIdentifier)
        }
    }

    private fun checkIfDomainEventAlreadyStored(domainEvent: StoreDomainEventEntry) =
            eventStorage[domainEvent.aggregateIdentifier]?.any { it.eventIdentifier == domainEvent.eventIdentifier }
                    ?: false


    override fun <EventType : DomainEvent<*>> readEvents(
            domainEventType: Class<EventType>,
            aggregateId: AggregateId,
            toEventTimestamp: Instant,
            toAggregateVersion: AggregateVersion?): List<EventType> =
            (eventStorage[aggregateId.toString()] ?: emptyList<StoreDomainEventEntry>())
                    .filter { !it.timestamp.isAfter(toEventTimestamp) }
                    .filter { storedEventVersion ->
                        toAggregateVersion
                                ?.toLong()
                                ?.let { toAggregateVersion -> storedEventVersion.aggregateVersion <= toAggregateVersion }
                                ?: true
                    }
                    .map { extractDomainEvent(domainEventType, it) }
}


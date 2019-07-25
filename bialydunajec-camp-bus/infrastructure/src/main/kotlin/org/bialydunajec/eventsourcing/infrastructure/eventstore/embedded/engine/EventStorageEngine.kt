package org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine

import org.bialydunajec.eventsourcing.domain.AggregateId
import org.bialydunajec.eventsourcing.domain.AggregateVersion
import org.bialydunajec.eventsourcing.domain.DomainEvent
import org.bialydunajec.eventsourcing.infrastructure.eventstore.EventSerializer
import java.time.Instant

internal interface EventStorageEngine {

    val eventSerializer: EventSerializer

    fun appendDomainEvent(domainEvent: DomainEvent<*>)

    fun <EventType : DomainEvent<*>> readEvents(domainEventType: Class<EventType>, aggregateId: AggregateId, toEventTimestamp: Instant, toAggregateVersion: AggregateVersion? = null): List<EventType>


}

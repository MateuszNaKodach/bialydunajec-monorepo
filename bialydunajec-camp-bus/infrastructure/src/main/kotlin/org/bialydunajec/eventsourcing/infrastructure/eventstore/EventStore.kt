package org.bialydunajec.eventsourcing.infrastructure.eventstore

import org.bialydunajec.eventsourcing.domain.AggregateId
import org.bialydunajec.eventsourcing.domain.DomainEvent
import org.bialydunajec.eventsourcing.infrastructure.eventbus.EventBus
import java.time.Instant

interface EventStore : EventBus {

    fun <EventType : DomainEvent<*>> readEvents(payloadClass: Class<EventType>, aggregateId: AggregateId): List<EventType>

    fun <EventType : DomainEvent<*>> readEvents(payloadClass: Class<EventType>, aggregateId: AggregateId, toTimestamp: Instant): List<EventType>

}

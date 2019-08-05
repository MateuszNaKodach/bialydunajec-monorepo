package org.bialydunajec.eventsourcing.infrastructure.eventstore

import org.bialydunajec.eventsourcing.domain.AggregateId
import org.bialydunajec.eventsourcing.domain.DomainEvent
import org.bialydunajec.eventsourcing.infrastructure.eventbus.EventBus
import reactor.core.publisher.Flux
import java.time.Instant

//TODO: Implement!
interface ReactorEventStore : EventBus {

    fun <EventType : DomainEvent<*>> streamEvents(payloadClass: Class<EventType>, aggregateId: AggregateId): Flux<DomainEvent<*>>

    fun <EventType : DomainEvent<*>> streamEvents(payloadClass: Class<EventType>, aggregateId: AggregateId, toTimestamp: Instant): Flux<DomainEvent<*>>

}

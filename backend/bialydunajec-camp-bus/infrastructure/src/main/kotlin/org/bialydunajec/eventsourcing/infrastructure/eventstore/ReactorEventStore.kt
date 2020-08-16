package org.bialydunajec.eventsourcing.infrastructure.eventstore

import org.bialydunajec.eventsourcing.domain.AggregateId
import org.bialydunajec.eventsourcing.domain.DomainEvent
//import reactor.core.publisher.Flux
import java.time.Instant

//TODO: Implement!
interface ReactorEventStore : EventStore {

   // fun <EventType : DomainEvent<*>> streamEvents(payloadClass: Class<EventType>, aggregateId: AggregateId): Flux<EventType>

   // fun <EventType : DomainEvent<*>> streamEvents(payloadClass: Class<EventType>, aggregateId: AggregateId, toTimestamp: Instant): Flux<EventType>

}

package org.bialydunajec.eventsourcing.infrastructure.eventstore

import org.bialydunajec.eventsourcing.domain.DomainEvent
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.EventStreamName
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.EventStreamVersion

class EventStream<EventType : DomainEvent<*>> internal constructor(
        val name: EventStreamName,
        val version: EventStreamVersion,
        val events: List<EventType>
) {
    val size: Int
        get() = events.size
}
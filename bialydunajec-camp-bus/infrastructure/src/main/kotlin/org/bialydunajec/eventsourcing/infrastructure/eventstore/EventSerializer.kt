package org.bialydunajec.eventsourcing.infrastructure.eventstore

import org.bialydunajec.eventsourcing.domain.DomainEvent

internal interface EventSerializer {

    fun serialize(domainEvents: List<DomainEvent<*>>): List<String> =
            domainEvents.map { serialize(it) }

    fun serialize(domainEvent: DomainEvent<*>): String

    fun <EventType : DomainEvent<*>> deserialize(domainEventType: Class<EventType>, serializedDomainEvents: List<String>): List<DomainEvent<*>> =
            serializedDomainEvents.map { deserialize(domainEventType, it) }

    fun <EventType : DomainEvent<*>> deserialize(domainEventType: Class<EventType>, serializedDomainEvent: String): DomainEvent<*>
}

package org.bialydunajec.eventsourcing.infrastructure.eventstore.serializer

import com.fasterxml.jackson.databind.ObjectMapper
import org.bialydunajec.eventsourcing.domain.DomainEvent
import org.bialydunajec.eventsourcing.infrastructure.eventstore.EventSerializer

internal class JacksonSerializer(private val objectMapper: ObjectMapper) : EventSerializer {

    override fun serialize(domainEvent: DomainEvent<*>): String =
            objectMapper.writeValueAsString(domainEvent)

    override fun <EventType : DomainEvent<*>> deserialize(domainEventType: Class<EventType>, serializedDomainEvent: String): DomainEvent<*> =
            objectMapper.readValue(serializedDomainEvent, domainEventType)
}

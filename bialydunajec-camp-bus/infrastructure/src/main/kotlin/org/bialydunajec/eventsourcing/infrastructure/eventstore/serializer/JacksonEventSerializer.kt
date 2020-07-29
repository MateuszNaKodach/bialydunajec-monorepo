package org.bialydunajec.eventsourcing.infrastructure.eventstore.serializer

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.bialydunajec.eventsourcing.domain.DomainEvent
import org.bialydunajec.eventsourcing.infrastructure.eventstore.EventSerializer

internal class JacksonEventSerializer(private val objectMapper: ObjectMapper = defaultObjectMapper()) : EventSerializer {

    override fun serialize(domainEvent: DomainEvent<*>): String =
            objectMapper.writeValueAsString(domainEvent)

    override fun <EventType : DomainEvent<*>> deserialize(domainEventType: Class<EventType>, serializedDomainEvent: String): EventType =
            objectMapper.readValue(serializedDomainEvent, domainEventType)

    companion object {
        fun defaultObjectMapper(): ObjectMapper = ObjectMapper().eventSerializerConfig()
    }
}

fun ObjectMapper.eventSerializerConfig(): ObjectMapper = this.registerKotlinModule()
        .registerModule(JavaTimeModule())
        .setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
        .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
        .disable(MapperFeature.AUTO_DETECT_IS_GETTERS)

package org.bialydunajec.eventsourcing.infrastructure.eventstore.mongodb

import com.fasterxml.jackson.databind.ObjectMapper
import org.bialydunajec.eventsourcing.domain.DomainEvent

internal class DomainEventToMongoDbDocumentMapper(private val objectMapper: ObjectMapper) {

    fun createEventDocuments(events: List<DomainEvent<*>>): List<DomainEventDocument> =
            events.map { createEventDocument(it) }

    private fun createEventDocument(event: DomainEvent<*>): DomainEventDocument =
            event.let {
                val aggregateName: String = it::aggregateType.get().canonicalName
                DomainEventDocument(
                        it.aggregateId.toString(),
                        aggregateName,
                        it.aggregateVersion.toLong(),
                        it.occurredAt,
                        objectMapper.writeValueAsString(it),
                        it.javaClass.canonicalName,
                        "",
                        it.domainEventId.toString(),
                        toEventName(it::class.simpleName),
                        1,
                        it.eventStreamType.canonicalName
                )
            }

    fun <EventType : DomainEvent<*>> extractDomainEvents(payloadClass: Class<EventType>, documents: List<DomainEventDocument>) =
            documents.map { extractDomainEvent(payloadClass, it) }

    private fun <EventType : DomainEvent<*>> extractDomainEvent(payloadClass: Class<EventType>, document: DomainEventDocument) =
            objectMapper.readValue(document.serializedPayload, payloadClass)


    companion object {
        private fun toEventName(text: String?) =
                text
                        ?.foldIndexed("") { i, acc, c -> acc + if (i == 0) c else (if (c.isUpperCase()) "_$c" else c) }
                        ?.toUpperCase()
                        ?: "UNKNOWN"
    }

}

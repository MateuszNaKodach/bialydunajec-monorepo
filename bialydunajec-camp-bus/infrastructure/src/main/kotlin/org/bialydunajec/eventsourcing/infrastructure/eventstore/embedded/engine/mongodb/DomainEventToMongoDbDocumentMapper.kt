package org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.mongodb

import org.bialydunajec.eventsourcing.domain.DomainEvent
import org.bialydunajec.eventsourcing.infrastructure.eventstore.EventSerializer
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.StoredDomainEventEntry

internal class DomainEventToMongoDbDocumentMapper(private val eventSerializer: EventSerializer) {

    fun createEventDocuments(events: List<DomainEvent<*>>): List<DomainEventDocument> =
            events
                    .map { toStoredDomainEventEntry(it) }
                    .map { toDomainEventDocument(it) }

    private fun toDomainEventDocument(event: StoredDomainEventEntry): DomainEventDocument =
            event.let {
                DomainEventDocument(
                        it.aggregateIdentifier,
                        it.aggregateType,
                        it.aggregateVersion,
                        it.timestamp,
                        it.serializedPayload,
                        it.payloadType,
                        it.serializedMetaData,
                        it.eventIdentifier,
                        it.eventType,
                        it.eventVersion,
                        it.eventStreamType
                )
            }

    fun <EventType : DomainEvent<*>> extractDomainEvents(payloadClass: Class<EventType>, documents: List<DomainEventDocument>) =
            documents.map { extractDomainEvent(payloadClass, it) }

    private fun <EventType : DomainEvent<*>> extractDomainEvent(payloadClass: Class<EventType>, document: DomainEventDocument) =
            eventSerializer.deserialize(payloadClass, document.serializedPayload)


    companion object {
        private fun toEventName(text: String?) =
                text
                        ?.foldIndexed("") { i, acc, c -> acc + if (i == 0) c else (if (c.isUpperCase()) "_$c" else c) }
                        ?.toUpperCase()
                        ?: "UNKNOWN"
    }

}

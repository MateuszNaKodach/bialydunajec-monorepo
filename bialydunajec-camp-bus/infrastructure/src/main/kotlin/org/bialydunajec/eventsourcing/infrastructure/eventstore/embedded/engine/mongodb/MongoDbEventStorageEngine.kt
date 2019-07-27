package org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.mongodb

import com.mongodb.DuplicateKeyException
import org.bialydunajec.eventsourcing.domain.AggregateId
import org.bialydunajec.eventsourcing.domain.AggregateVersion
import org.bialydunajec.eventsourcing.domain.DomainEvent
import org.bialydunajec.eventsourcing.infrastructure.eventstore.EventSerializer
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.AbstractEventStorageEngine
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.StoreDomainEventEntry
import org.bialydunajec.eventsourcing.infrastructure.eventstore.exception.DomainEventAlreadyStoredException
import java.time.Instant

internal class MongoDbEventStorageEngine(
        eventSerializer: EventSerializer,
        private val documents: DomainEventDocuments
) : AbstractEventStorageEngine(eventSerializer) {

    override fun storeEvent(domainEvent: StoreDomainEventEntry) {
        toDomainEventDocument(domainEvent)
                .let { tryToSave(it) }
    }

    private fun tryToSave(document: DomainEventDocument) {
        try {
            documents.save(document)
        } catch (exception: DuplicateKeyException) {
            throw DomainEventAlreadyStoredException(document.eventIdentifier, exception)
        }
    }

    private fun toDomainEventDocument(event: StoreDomainEventEntry): DomainEventDocument =
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

    override fun <EventType : DomainEvent<*>> readEvents(
            domainEventType: Class<EventType>,
            aggregateId: AggregateId,
            toEventTimestamp: Instant,
            toAggregateVersion: AggregateVersion?
    ): List<EventType> =
            if (toAggregateVersion == null) {
                documents.findAllByEventStreamTypeAndTimestampLessThanEqualOrderByAggregateVersionAsc(
                        domainEventType.canonicalName,
                        toEventTimestamp)
            } else {
                documents.findAllByEventStreamTypeAndTimestampLessThanEqualAndAggregateVersionLessThanEqualOrderByAggregateVersionAsc(
                        domainEventType.canonicalName,
                        toEventTimestamp,
                        toAggregateVersion.toLong())
            }.map { toStoreDomainEventEntry(it) }.let { extractDomainEvents(domainEventType, it) }

    private fun toStoreDomainEventEntry(document: DomainEventDocument) =
            document.let {
                StoreDomainEventEntry(
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

}

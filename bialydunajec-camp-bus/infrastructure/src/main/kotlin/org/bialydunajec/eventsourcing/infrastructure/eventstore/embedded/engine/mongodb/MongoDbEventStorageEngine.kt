package org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.mongodb

import com.mongodb.DuplicateKeyException
import org.bialydunajec.eventsourcing.domain.AggregateId
import org.bialydunajec.eventsourcing.domain.DomainEvent
import org.bialydunajec.eventsourcing.infrastructure.eventstore.EventSerializer
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.AbstractEventStorageEngine
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.EventStreamName
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.ExpectedEventStreamVersion
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.StoreDomainEventEntry
import org.bialydunajec.eventsourcing.infrastructure.eventstore.exception.DomainEventAlreadyStoredException
import java.time.Instant

internal class MongoDbEventStorageEngine(
        eventSerializer: EventSerializer,
        private val documents: DomainEventDocuments
) : AbstractEventStorageEngine(eventSerializer) {

    //TODO: Use eventStreamName!
    override fun storeEvent(streamName: EventStreamName, domainEvent: StoreDomainEventEntry, expectedVersion: ExpectedEventStreamVersion) {
        toDomainEventDocument(domainEvent)
                .let { tryToSave(it, expectedVersion) }
    }

    //TODO: Check expected version!
    private fun tryToSave(document: DomainEventDocument, expectedVersion: ExpectedEventStreamVersion) {
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
            toEventTimestamp: Instant
    ): List<EventType> =
            documents.findAllByAggregateIdentifierAndEventStreamTypeAndTimestampLessThanEqualOrderByAggregateVersionAsc(
                    aggregateId.toString(),
                    domainEventType.canonicalName,
                    toEventTimestamp
            ).map { toStoreDomainEventEntry(it) }.let { extractDomainEvents(domainEventType, it) }

    private fun toStoreDomainEventEntry(document: DomainEventDocument) =
            document.let {
                StoreDomainEventEntry(
                        it.aggregateIdentifier,
                        it.aggregateType,
                        //it.aggregateVersion,
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

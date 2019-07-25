package org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.mongodb

import org.bialydunajec.eventsourcing.domain.AggregateId
import org.bialydunajec.eventsourcing.domain.DomainEvent
import org.bialydunajec.eventsourcing.infrastructure.eventstore.EventSerializer
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.EventStorageEngine

internal class MongoDbEventStorageEngine(
        eventSerializer: EventSerializer,
        private val domainEventDocumentDAO: DomainEventDocumentDAO
) : EventStorageEngine {

    private val domainEventDocumentMapper: DomainEventToMongoDbDocumentMapper = DomainEventToMongoDbDocumentMapper(eventSerializer)

    override fun appendEvents(domainEvents: List<DomainEvent<*>>) {
        domainEventDocumentDAO.saveAll(domainEventDocumentMapper.createEventDocuments(domainEvents))
    }

    override fun <EventType : DomainEvent<*>> readEvents(domainEventType: Class<EventType>, aggregateId: AggregateId): List<DomainEvent<*>> =
            domainEventDocumentDAO.findAllByEventStreamType(domainEventType.canonicalName).let {
                domainEventDocumentMapper.extractDomainEvents(domainEventType, it)
            }

}

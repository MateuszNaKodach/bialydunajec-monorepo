package org.bialydunajec.eventsourcing.infrastructure.eventstore.mongodb

import com.fasterxml.jackson.databind.ObjectMapper
import org.bialydunajec.eventsourcing.domain.AggregateId
import org.bialydunajec.eventsourcing.domain.DomainEvent
import org.bialydunajec.eventsourcing.infrastructure.eventstore.EventStore

internal class MongoDbEventStore(
        objectMapper: ObjectMapper,
        private val domainEventDocumentDAO: DomainEventDocumentDAO
) : EventStore {

    private val domainEventDocumentMapper: DomainEventToMongoDbDocumentMapper = DomainEventToMongoDbDocumentMapper(objectMapper)

    override fun saveAll(events: List<DomainEvent<*>>) {
        domainEventDocumentDAO.saveAll(domainEventDocumentMapper.createEventDocuments(events))
    }

    override fun <EventType : DomainEvent<*>> loadEvents(payloadClass: Class<EventType>, aggregateId: AggregateId): List<DomainEvent<*>> =
            domainEventDocumentDAO.findAllByEventStreamType(payloadClass.canonicalName).let {
                domainEventDocumentMapper.extractDomainEvents(payloadClass, it)
            }

}

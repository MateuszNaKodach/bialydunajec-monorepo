package org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.mongodb

import org.springframework.data.mongodb.repository.MongoRepository


internal interface DomainEventDocumentDAO : MongoRepository<DomainEventDocument, String> {

    fun findAllByEventStreamType(eventStreamType: String): List<DomainEventDocument>
}

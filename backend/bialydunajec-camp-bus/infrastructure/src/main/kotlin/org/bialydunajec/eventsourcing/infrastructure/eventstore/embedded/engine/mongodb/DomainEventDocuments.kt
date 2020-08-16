package org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.mongodb

import org.springframework.data.mongodb.repository.MongoRepository
import java.time.Instant


internal interface DomainEventDocuments : MongoRepository<DomainEventDocument, String> {

    fun findAllByAggregateIdentifierAndEventStreamTypeAndTimestampLessThanEqualOrderByAggregateVersionAsc(
            aggregateIdentifier: String,
            eventStreamType: String,
            toTimestamp: Instant
    ): List<DomainEventDocument>

    fun findAllByAggregateIdentifierAndEventStreamTypeAndTimestampLessThanEqualAndAggregateVersionLessThanEqualOrderByAggregateVersionAsc(
            aggregateIdentifier: String,
            eventStreamType: String,
            toTimestamp: Instant,
            toAggregateVersion: Long
    ): List<DomainEventDocument>

}

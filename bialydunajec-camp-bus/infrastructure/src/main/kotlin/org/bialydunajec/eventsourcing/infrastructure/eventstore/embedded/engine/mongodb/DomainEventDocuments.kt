package org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.mongodb

import org.springframework.data.mongodb.repository.MongoRepository
import java.time.Instant


internal interface DomainEventDocuments : MongoRepository<DomainEventDocument, String> {

    fun findAllByEventStreamTypeAndTimestampLessThanEqualOrderByAggregateVersionAsc(eventStreamType: String, toTimestamp: Instant): List<DomainEventDocument>

    fun findAllByEventStreamTypeAndTimestampLessThanEqualAndAggregateVersionLessThanEqualOrderByAggregateVersionAsc(
            eventStreamType: String,
            toTimestamp: Instant,
            toAggregateVersion: Long
    ): List<DomainEventDocument>

}

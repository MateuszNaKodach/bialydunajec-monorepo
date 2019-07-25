package org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.mongodb

import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant


@CompoundIndexes(
        CompoundIndex(
                name = "sequenced_events",
                unique = true, def = "{'aggregateIdentifier' : 1, 'aggregateVersion' : 1}")
)
@Document(collection = "domainevents")
internal class DomainEventDocument(
        val aggregateIdentifier: String,
        val aggregateType: String,
        val aggregateVersion: Long,
        val timestamp: Instant,
        val serializedPayload: String,
        val payloadType: String,
        val serializedMetaData: String,

        @Indexed(unique = true)
        val eventIdentifier: String,
        val eventType: String,
        val eventVersion: Long,
        val eventStreamType: String
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DomainEventDocument

        if (eventIdentifier != other.eventIdentifier) return false

        return true
    }

    override fun hashCode(): Int {
        return eventIdentifier.hashCode()
    }
}

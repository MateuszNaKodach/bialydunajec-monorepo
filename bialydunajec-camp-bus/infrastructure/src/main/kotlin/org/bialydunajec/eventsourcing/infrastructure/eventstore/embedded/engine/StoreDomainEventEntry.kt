package org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine

import java.time.Instant

internal class StoreDomainEventEntry(
        val aggregateIdentifier: String,
        val aggregateType: String,
        val aggregateVersion: Long,
        val timestamp: Instant,
        val serializedPayload: String,
        val payloadType: String,
        val serializedMetaData: String,
        val eventIdentifier: String,
        val eventType: String,
        val eventVersion: Long,
        val eventStreamType: String
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StoreDomainEventEntry

        if (eventIdentifier != other.eventIdentifier) return false

        return true
    }

    override fun hashCode(): Int {
        return eventIdentifier.hashCode()
    }
}

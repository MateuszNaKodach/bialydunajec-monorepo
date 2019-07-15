package org.bialydunajec.eventsourcing.domain

import java.time.Instant

interface DomainEvent<AggregateIdType : AggregateId> {
    val aggregateId: AggregateIdType
    val aggregateVersion: AggregateVersion
    val domainEventId: DomainEventId
    val occurredAt: Instant
}
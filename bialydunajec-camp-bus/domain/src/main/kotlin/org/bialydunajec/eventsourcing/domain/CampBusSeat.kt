package org.bialydunajec.eventsourcing.domain

import org.bialydunajec.campbus.domain.AggregateVersion
import org.bialydunajec.campbus.domain.SeatId
import java.time.Instant
import java.util.*

typealias TimeProvider = () -> Instant

interface AggregateRoot<AggregateIdType : AggregateId>
class DomainEventId(val id: String = UUID.randomUUID().toString())
interface Command<AggregateIdType : AggregateId> {
    val aggregateId: SeatId
    val aggregateVersion: AggregateVersion
}

interface DomainEvent<AggregateIdType : AggregateId> {
    val aggregateId: AggregateIdType
    val aggregateVersion: AggregateVersion
    val domainEventId: DomainEventId
    val occurredAt: Instant
}
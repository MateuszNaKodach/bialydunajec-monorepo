package org.bialydunajec.eventsourcing.testfixtures

import org.bialydunajec.eventsourcing.domain.*
import java.time.Instant
import java.util.*

class SampleAggregateId(id: String = UUID.randomUUID().toString()) : AggregateId(id)
class AnotherSampleAggregateId(id: String = UUID.randomUUID().toString()) : AggregateId(id)

sealed class SampleAggregateRoot(
        currentTimeProvider: TimeProvider,
        aggregateId: SampleAggregateId,
        changes: List<SampleDomainEvent>,
        aggregateVersion: AggregateVersion)
    : EventSourcedAggregateRoot<SampleAggregateId, SampleDomainCommand, SampleDomainEvent, SampleAggregateRoot>(
        currentTimeProvider, aggregateId, aggregateVersion, changes, SampleDomainEvent::class)

sealed class SampleDomainCommand(
        aggregateId: SampleAggregateId,
        aggregateVersion: AggregateVersion
) : DomainCommand<SampleAggregateId, SampleDomainCommand>(aggregateId, aggregateVersion)

sealed class SampleDomainEvent(
        aggregateId: SampleAggregateId,
        aggregateVersion: AggregateVersion,
        occurredAt: Instant,
        domainEventId: DomainEventId = DomainEventId()
) : DomainEvent<SampleAggregateId, SampleDomainEvent>(aggregateId, aggregateVersion, domainEventId, occurredAt, SampleAggregateRoot::class.java, SampleDomainEvent::class.java) {
    class WithNoAdditionalValues(aggregateId: SampleAggregateId, aggregateVersion: AggregateVersion, occurredAt: Instant) : SampleDomainEvent(aggregateId, aggregateVersion, occurredAt)
    class WithAdditionalIntValue(aggregateId: SampleAggregateId, aggregateVersion: AggregateVersion, occurredAt: Instant, val intValue: Int) : SampleDomainEvent(aggregateId, aggregateVersion, occurredAt)
    class WithAnotherAggregateId(aggregateId: SampleAggregateId, aggregateVersion: AggregateVersion, occurredAt: Instant, val anotherAggregateId: AnotherSampleAggregateId) : SampleDomainEvent(aggregateId, aggregateVersion, occurredAt)
}

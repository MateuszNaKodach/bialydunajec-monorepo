package org.bialydunajec.eventsourcing.testfixtures

import org.bialydunajec.eventsourcing.domain.*
import java.time.Instant
import java.util.*

class SampleAggregateId(id: String = UUID.randomUUID().toString()) : AggregateId(id)
class AnotherSampleAggregateId(id: String = UUID.randomUUID().toString()) : AggregateId(id)

sealed class SampleAggregateRoot(
        currentTimeProvider: TimeProvider,
        aggregateId: SampleAggregateId,
        changes: List<SampleDomainEvent>)
    : EventSourcedAggregateRoot<SampleAggregateId, SampleDomainCommand, SampleDomainEvent, SampleAggregateRoot>(
        currentTimeProvider, aggregateId, changes, SampleDomainEvent::class)

sealed class SampleDomainCommand(
        aggregateId: SampleAggregateId
) : DomainCommand<SampleAggregateId>(aggregateId)

sealed class SampleDomainEvent(
        aggregateId: SampleAggregateId,
        occurredAt: Instant,
        domainEventId: DomainEventId = DomainEventId()
) : DomainEvent<SampleAggregateId>(aggregateId, domainEventId, occurredAt, SampleAggregateRoot::class.java, SampleDomainEvent::class.java) {
    class WithNoAdditionalValues(aggregateId: SampleAggregateId, occurredAt: Instant) : SampleDomainEvent(aggregateId, occurredAt)
    class WithAdditionalIntValue(aggregateId: SampleAggregateId, occurredAt: Instant, val intValue: Int) : SampleDomainEvent(aggregateId, occurredAt)
    class WithAnotherAggregateId(aggregateId: SampleAggregateId, occurredAt: Instant, val anotherAggregateId: AnotherSampleAggregateId) : SampleDomainEvent(aggregateId, occurredAt)
}

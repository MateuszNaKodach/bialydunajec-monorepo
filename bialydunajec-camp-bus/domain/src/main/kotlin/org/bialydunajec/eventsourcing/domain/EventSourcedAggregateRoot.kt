package org.bialydunajec.eventsourcing.domain

interface EventSourcedAggregateRoot<AggregateIdType : AggregateId, AggregateEventType : DomainEvent<AggregateIdType>>
    : AggregateRoot<AggregateIdType, AggregateEventType> {
    val changes: List<AggregateEventType>
}

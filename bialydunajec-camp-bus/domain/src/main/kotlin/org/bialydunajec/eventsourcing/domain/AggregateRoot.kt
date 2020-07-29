package org.bialydunajec.eventsourcing.domain

interface AggregateRoot<AggregateIdType : AggregateId, AggregateEventType: DomainEvent<AggregateIdType>> {
    val aggregateId: AggregateIdType
}


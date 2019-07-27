package org.bialydunajec.eventsourcing.domain

import kotlin.reflect.KClass

interface EventSourcedAggregateRoot<AggregateIdType : AggregateId, AggregateEventType : DomainEvent<AggregateIdType>>
    : AggregateRoot<AggregateIdType, AggregateEventType> {
    val changes: List<AggregateEventType>
    val eventsStreamType: KClass<AggregateEventType>
}

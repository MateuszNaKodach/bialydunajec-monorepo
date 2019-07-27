package org.bialydunajec.eventsourcing.domain

import kotlin.reflect.KClass

abstract class EventSourcedAggregateRoot<AggregateIdType : AggregateId, AggregateEventType : DomainEvent<AggregateIdType>>(
        protected val currentTimeProvider: TimeProvider,
        override val aggregateId: AggregateIdType,
        override val aggregateVersion: AggregateVersion,
        val changes: List<AggregateEventType>,
        val eventsStreamType: KClass<AggregateEventType>
) : AggregateRoot<AggregateIdType, AggregateEventType>


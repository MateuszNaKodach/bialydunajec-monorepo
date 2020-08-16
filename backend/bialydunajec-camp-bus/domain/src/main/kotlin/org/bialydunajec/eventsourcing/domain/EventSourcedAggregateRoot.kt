package org.bialydunajec.eventsourcing.domain

import kotlin.reflect.KClass

abstract class EventSourcedAggregateRoot<
        AggregateIdType : AggregateId,
        AggregateCommandType : DomainCommand<AggregateIdType>,
        AggregateEventType : DomainEvent<AggregateIdType>,
        AggregateRootType : EventSourcedAggregateRoot<AggregateIdType, AggregateCommandType, AggregateEventType, AggregateRootType>>(
        protected val currentTimeProvider: TimeProvider,
        override val aggregateId: AggregateIdType,
        val changes: List<AggregateEventType>,
        val eventsStreamType: KClass<AggregateEventType>
) : AggregateRoot<AggregateIdType, AggregateEventType> {

    fun replayEvent(event: AggregateEventType) = applyEvent(event, EventApplyingMode.REPLAY_HISTORY)

    private fun applyEvent(event: AggregateEventType, mode: EventApplyingMode = EventApplyingMode.DEFAULT): AggregateRootType {
        return composeOf(
                when (mode) {
                    EventApplyingMode.APPLY_NEW_CHANGE -> changes.plus(event)
                    EventApplyingMode.REPLAY_HISTORY -> changes
                },
                event
        )
    }


    fun handle(command: AggregateCommandType): AggregateRootType =
            applyEvent(process(command))

    abstract fun process(command: AggregateCommandType): AggregateEventType

    protected abstract fun composeOf(uncommittedHistory: List<AggregateEventType>, lastEvent: AggregateEventType): AggregateRootType

}


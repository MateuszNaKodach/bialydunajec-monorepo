package org.bialydunajec.eventsourcing.domain

import kotlin.reflect.KClass

abstract class EventSourcedAggregateRoot<
        AggregateIdType : AggregateId,
        AggregateCommandType : DomainCommand<AggregateIdType>,
        AggregateEventType : DomainEvent<AggregateIdType>,
        AggregateRootType : EventSourcedAggregateRoot<AggregateIdType, AggregateCommandType, AggregateEventType, AggregateRootType>>(
        protected val currentTimeProvider: TimeProvider,
        override val aggregateId: AggregateIdType,
        override val aggregateVersion: AggregateVersion,
        val changes: List<AggregateEventType>,
        val eventsStreamType: KClass<AggregateEventType>
) : AggregateRoot<AggregateIdType, AggregateEventType> {

    fun replayEvent(event: AggregateEventType) = applyEvent(event, EventApplyingMode.REPLAY_HISTORY)

    private fun applyEvent(event: AggregateEventType, mode: EventApplyingMode = EventApplyingMode.DEFAULT): AggregateRootType {
        if (aggregateVersion != event.aggregateVersion) {
            throw BrokenEventSequenceException(aggregateVersion, event.aggregateVersion)
        }
        return composeOf(
                when (mode) {
                    EventApplyingMode.APPLY_NEW_CHANGE -> changes.plus(event)
                    EventApplyingMode.REPLAY_HISTORY -> changes
                },
                event,
                event.aggregateVersion.increase()
        )
    }


    fun handle(command: AggregateCommandType): AggregateRootType =
            doIfCommandMatchedAggregateVersion(command) { applyEvent(process(command)) }


    fun execute(command: AggregateCommandType): AggregateEventType =
            doIfCommandMatchedAggregateVersion(command) { process(command) }

    private fun <R> doIfCommandMatchedAggregateVersion(command: AggregateCommandType, doIfMatched: () -> R): R {
        if (this.aggregateVersion != command.aggregateVersion) {
            throw AggregateVersionMismatchException(aggregateVersion, command.aggregateVersion)
        }
        return doIfMatched()
    }

    abstract fun process(command: AggregateCommandType): AggregateEventType

    protected abstract fun composeOf(uncommittedHistory: List<AggregateEventType>, lastEvent: AggregateEventType, nextAggregateVersion: AggregateVersion): AggregateRootType

}


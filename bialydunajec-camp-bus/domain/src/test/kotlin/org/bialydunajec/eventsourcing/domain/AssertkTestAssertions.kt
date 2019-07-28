package org.bialydunajec.eventsourcing.domain

import assertk.Assert
import assertk.assertions.support.expected
import assertk.assertions.support.show
import kotlin.reflect.KClass

fun <AggregateIdType : AggregateId,
        AggregateCommandType : DomainCommand<AggregateIdType>,
        AggregateEventType : DomainEvent<AggregateIdType>,
        AggregateRootType : EventSourcedAggregateRoot<AggregateIdType, AggregateCommandType, AggregateEventType, AggregateRootType>>
        Assert<EventSourcedAggregateRoot<AggregateIdType, AggregateCommandType, AggregateEventType, AggregateRootType>>.expectEventOccurredLastlyIs(expectedEvent: AggregateEventType) = given { actual ->
    val lastEvent = actual.changes.last()
    if (lastEvent == expectedEvent) return
    expected("last event: ${show(expectedEvent)} but was: ${show(actual.changes.last())}")
}

fun <AggregateIdType : AggregateId,
        AggregateCommandType : DomainCommand<AggregateIdType>,
        AggregateEventType : DomainEvent<AggregateIdType>,
        AggregateRootType : EventSourcedAggregateRoot<AggregateIdType, AggregateCommandType, AggregateEventType, AggregateRootType>>
        Assert<EventSourcedAggregateRoot<AggregateIdType, AggregateCommandType, AggregateEventType, AggregateRootType>>.expectOnlyEvent(expectedEvent: AggregateEventType) = given { actual ->
    val changesSize = actual.changes.size
    when (actual.changes.size) {
        in 0..0 -> expected("only event: ${show(actual.changes.first())} but no events occurred")
        in 1..1 -> if (actual.changes.first() != expectedEvent) {
            expected("only event: ${show(expectedEvent)} but occurred: ${show(actual.changes.first())}")
        }
        in 2..changesSize -> expected("only event: ${show(actual.changes.first())} but occurred events: ${show(actual.changes.contentToString())}")
        else -> return
    }
}

fun <AggregateIdType : AggregateId,
        AggregateCommandType : DomainCommand<AggregateIdType>,
        AggregateEventType : DomainEvent<AggregateIdType>,
        AggregateRootType : EventSourcedAggregateRoot<AggregateIdType, AggregateCommandType, AggregateEventType, AggregateRootType>,
        T: AggregateEventType>
        Assert<EventSourcedAggregateRoot<AggregateIdType, AggregateCommandType, AggregateEventType, AggregateRootType>>.expectOnlyEventWithType(eventType: KClass<T>) = given { actual ->
    val changesSize = actual.changes.size
    when (actual.changes.size) {
        in 0..0 -> expected("only event: ${show(actual.changes.first())} but no events occurred")
        in 1..1 -> if (actual.changes.first()::class != eventType) {
            expected("only event with type: ${show(eventType)} but occurred: ${show(actual.changes.first())}")
        }
        in 2..changesSize -> expected("only event: ${show(actual.changes.first())} but occurred events: ${show(actual.changes.contentToString())}")
        else -> return
    }
}

fun <AggregateIdType : AggregateId,
        AggregateCommandType : DomainCommand<AggregateIdType>,
        AggregateEventType : DomainEvent<AggregateIdType>,
        AggregateRootType : EventSourcedAggregateRoot<AggregateIdType, AggregateCommandType, AggregateEventType, AggregateRootType>>
        Assert<EventSourcedAggregateRoot<AggregateIdType, AggregateCommandType, AggregateEventType, AggregateRootType>>.expectNoEvents() = given { actual ->
    if (actual.changes.isEmpty()) return
    expected("no event, but occurred events: ${show(actual.changes.contentToString())}")
}

fun List<Any>.contentToString() = this.joinToString(
        separator = ", ",
        prefix = "[",
        postfix = "]"
)

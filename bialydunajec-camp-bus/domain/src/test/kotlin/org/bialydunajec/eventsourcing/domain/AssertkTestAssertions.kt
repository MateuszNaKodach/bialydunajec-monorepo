package org.bialydunajec.eventsourcing.domain

import assertk.Assert
import assertk.assertions.support.expected
import assertk.assertions.support.show
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

fun <AggregateIdType : AggregateId,
        AggregateCommandType : DomainCommand<AggregateIdType>,
        AggregateEventType : DomainEvent<AggregateIdType>,
        AggregateRootType : EventSourcedAggregateRoot<AggregateIdType, AggregateCommandType, AggregateEventType, AggregateRootType>>
        Assert<EventSourcedAggregateRoot<AggregateIdType, AggregateCommandType, AggregateEventType, AggregateRootType>>.expectEventOccurredLastlyIs(expectedEvent: AggregateEventType) = given { actual ->
    val lastEvent = actual.changes.last()
    if (equalsByPropertiesIgnoresDomainEventId(lastEvent, expectedEvent)) return
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
        in 1..1 -> if (notEqualByPropertiesIgnoredDomainEventId(actual.changes.first(), expectedEvent)) {
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
        T : AggregateEventType>
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


/**
 * This equals method does not takes domainEventId into account
 */
fun <AggregateIdType : AggregateId, EventType : DomainEvent<AggregateIdType>> Assert<EventType>.isEqualsToByProperties(e1: EventType, e2: EventType) {
    val notMatchedPropertiesNames = e1::class.memberProperties
            .map { it.name }
            .filter { it != "aggregateId" }
            .filter { readInstanceProperty(e1, it) != readInstanceProperty(e2, it) }

    if (notMatchedPropertiesNames.isEmpty()) return

    expected("properties: ${notMatchedPropertiesNames.contentToString()} not matched in events: $e1 and $e2")
}

@Suppress("UNCHECKED_CAST")
fun readInstanceProperty(instance: Any, propertyName: String): Any? {
    val property = instance::class.memberProperties
            .first { it.name == propertyName } as KProperty1<Any, *>
    return property.get(instance)
}

private fun <AggregateIdType : AggregateId, EventType : DomainEvent<AggregateIdType>> equalsByPropertiesIgnoresDomainEventId(e1: EventType, e2: EventType) =
        equalsByProperties(e1, e2, setOf("domainEventId"))


private fun <AggregateIdType : AggregateId, EventType : DomainEvent<AggregateIdType>> equalsByProperties(e1: EventType, e2: EventType, ignoredProperties: Collection<String> = setOf()) =
        e1::class.memberProperties
                .map { it.name }
                .filter { ignoredProperties.isEmpty() || !ignoredProperties.contains(it) }
                .all { readInstanceProperty(e1, it) == readInstanceProperty(e2, it) }

private fun <AggregateIdType : AggregateId, EventType : DomainEvent<AggregateIdType>> notEqualByPropertiesIgnoredDomainEventId(e1: EventType, e2: EventType) =
        equalsByPropertiesIgnoresDomainEventId(e1, e2).not()

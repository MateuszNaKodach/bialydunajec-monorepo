package org.bialydunajec.campedition.application

import assertk.Assert
import assertk.assertThat
import assertk.assertions.support.expected
import assertk.assertions.support.show
import org.bialydunajec.campedition.domain.campedition.CampEditionEvent
import org.bialydunajec.ddd.application.base.query.Query
import org.bialydunajec.ddd.application.base.query.QueryGateway
import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.ddd.domain.base.event.DomainEventsRecorder
import org.bialydunajec.ddd.domain.base.event.InMemoryDomainEventsRecorder
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

abstract class TestFixtureExpect<QueryType: Query, QueryGatewayType: QueryGateway<QueryType>>(
        val queryGateway: QueryGatewayType,
        val domainEvents: InMemoryDomainEventsRecorder,
) {

    inline infix fun <reified T : CampEditionEvent> thenExpect(event: () -> T): TestFixtureExpect<QueryType, QueryGatewayType> = apply {
        assertThat(domainEvents).publishedOnly<T>().equalsToDomainEvent(event())
    }

    inline infix fun <reified T : CampEditionEvent> thenExpect(event: T): TestFixtureExpect<QueryType, QueryGatewayType> = apply {
        assertThat(domainEvents).publishedLastly<T>().equalsToDomainEvent(event)
    }

    infix fun thenExpect(noEvents: NoEvents): TestFixtureExpect<QueryType, QueryGatewayType> = apply {
        assertThat(domainEvents).publishedNone()
    }

    inline infix fun <reified R> andResultOf(query: QueryType): Assert<R> = assertThat(queryGateway.process(query) as R)

    inline infix fun <reified R> andResultOf(query: (queryGateway: QueryGatewayType) -> R): Assert<R> = assertThat(query(queryGateway))

    inline fun <reified R> andResultOf(query: QueryType, assertions: Assert<R>.() -> Unit): TestFixtureExpect<QueryType, QueryGatewayType> {
        return andResultOf({ process(query) as R }, assertions)
    }

    inline fun <reified R> andResultOf(query: QueryGatewayType.() -> R, assertions: Assert<R>.() -> Unit): TestFixtureExpect<QueryType, QueryGatewayType> = apply {
        assertions(assertThat(query(queryGateway)))
    }

}

inline fun <reified EventType : DomainEvent<*>> Assert<DomainEventsRecorder>.publishedLastly(): Assert<EventType> =
        published(eventRetriever = { it.last() })

inline fun <reified EventType : DomainEvent<*>> Assert<DomainEventsRecorder>.published(index: Int): Assert<EventType> =
        published(eventRetriever = { it.getOrNull(index) })

inline fun <reified EventType : DomainEvent<*>> Assert<DomainEventsRecorder>.published(crossinline eventRetriever: (domainEvents: List<DomainEvent<*>>) -> DomainEvent<*>?): Assert<EventType> = transform { actual ->
    if (actual.recorded.isEmpty()) {
        expected("published event but nothing was published")
    } else {
        val lastPublished = eventRetriever(actual.recorded)
                ?: expected("published event of type ${show(EventType::class.simpleName)} but was ${show("null")}")
        if (lastPublished is EventType) {
            return@transform lastPublished
        } else {
            expected("published event of type ${show(EventType::class.simpleName)} but was ${show(lastPublished::class.simpleName)}")
        }
    }
}

inline fun <reified EventType : DomainEvent<*>> Assert<DomainEventsRecorder>.publishedOnly(): Assert<EventType> = transform { actual ->
    when (actual.recorded.size) {
        in 0..0 -> expected("only one event but no events occurred")
        in 1..1 -> {
            val published = actual.recorded.first()
            if (published is EventType) {
                return@transform published
            } else {
                expected("published event of type ${show(EventType::class.simpleName)} but was ${show(actual.recorded.first()::class.simpleName)}")
            }
        }
        in 2..actual.recorded.size -> expected("only event: ${show(actual.recorded.first())} but occurred events: ${show(actual.recorded.contentToString())}")
        else -> return@transform actual.recorded.first() as EventType
    }
}

fun Assert<DomainEventsRecorder>.publishedNone() = given { actual ->
    if (actual.recorded.isNotEmpty()) {
        expected("none event but occurred events: ${show(actual.recorded.contentToString())}")
    } else {
        return
    }
}

inline fun <reified EventType : DomainEvent<*>> Assert<EventType>.equalsToDomainEvent(event: EventType): Assert<EventType> = transform { actual ->
    if (equalsByPropertiesIgnoresDomainEventIdAndOccurredAt(actual, event).not()) {
        expected("event: ${show(event)} but occurred: ${show(actual)}")
    } else {
        return@transform actual
    }
}

fun List<Any>.contentToString() = this.joinToString(
        separator = ", ",
        prefix = "[",
        postfix = "]"
)

fun equalsByPropertiesIgnoresDomainEventIdAndOccurredAt(e1: Any, e2: Any) =
        equalsByProperties(e1, e2, setOf("domainEventId", "occurredAt"))

fun equalsByProperties(e1: Any, e2: Any, ignoredProperties: Collection<String> = setOf()) =
        e1::class.memberProperties
                .map { it.name }
                .filter { ignoredProperties.isEmpty() || !ignoredProperties.contains(it) }
                .all { readInstanceProperty(e1, it) == readInstanceProperty(e2, it) }

@Suppress("UNCHECKED_CAST")
fun readInstanceProperty(instance: Any, propertyName: String): Any? {
    val property = instance::class.memberProperties
            .first { it.name == propertyName } as KProperty1<Any, *>
    return property.get(instance)
}

//IntelliJ bug: move it at once to another file
object NoEvents
typealias noEvents = NoEvents

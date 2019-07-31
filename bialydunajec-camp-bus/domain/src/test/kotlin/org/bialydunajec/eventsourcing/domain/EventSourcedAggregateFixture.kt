package org.bialydunajec.eventsourcing.domain

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import kotlin.reflect.KClass

class EventSourcedAggregateTestFixture<AggregateIdType : AggregateId,
        AggregateCommandType : DomainCommand<AggregateIdType>,
        AggregateEventType : DomainEvent<AggregateIdType>,
        AggregateRootType : EventSourcedAggregateRoot<AggregateIdType, AggregateCommandType, AggregateEventType, AggregateRootType>>(
        private val aggregate: EventSourcedAggregateRoot<AggregateIdType, AggregateCommandType, AggregateEventType, AggregateRootType>
) {

    fun withNoPriorActivity() = EventSourcedTestFixtureWhen(aggregate)

    infix fun withPriorEvent(block: () -> AggregateEventType) =
            EventSourcedTestFixtureWhen(aggregate.replayEvent(block.invoke()))

    infix fun withPriorEvents(block: () -> List<AggregateEventType>) =
            EventSourcedTestFixtureWhen(block.invoke().fold(aggregate) { acc, event: AggregateEventType -> acc.replayEvent(event) })

}

class EventSourcedTestFixtureWhen<AggregateIdType : AggregateId,
        AggregateCommandType : DomainCommand<AggregateIdType>,
        AggregateEventType : DomainEvent<AggregateIdType>,
        AggregateRootType : EventSourcedAggregateRoot<AggregateIdType, AggregateCommandType, AggregateEventType, AggregateRootType>>(
        private val aggregate: EventSourcedAggregateRoot<AggregateIdType, AggregateCommandType, AggregateEventType, AggregateRootType>
) {

    infix fun whenCommand(block: () -> AggregateCommandType) =
            try {
                EventSourcedTestFixtureExpect(aggregate.handle(block.invoke()), null)
            } catch (e: Exception) {
                EventSourcedTestFixtureExpect(aggregate, e)
            }

}

class EventSourcedTestFixtureExpect<AggregateIdType : AggregateId,
        AggregateCommandType : DomainCommand<AggregateIdType>,
        AggregateEventType : DomainEvent<AggregateIdType>,
        AggregateRootType : EventSourcedAggregateRoot<AggregateIdType, AggregateCommandType, AggregateEventType, AggregateRootType>>(
        private val aggregate: EventSourcedAggregateRoot<AggregateIdType, AggregateCommandType, AggregateEventType, AggregateRootType>,
        private val exception: Exception?
) {

    infix fun <T : AggregateEventType> thenExpectEventWithType(block: () -> KClass<T>) =
            assertAll {
                assertThat(exception).isNull()
                assertThat(aggregate).expectOnlyEventWithType(block.invoke())
            }

    infix fun thenExpectEvent(block: () -> AggregateEventType) =
            assertAll {
                assertThat(exception).isNull()
                assertThat(aggregate).expectOnlyEvent(block.invoke())
            }

    fun thenExpectException() =
            assertThat(exception).isNotNull()

    fun thenExpectNoEvents() =
            apply {
                assertThat(aggregate).expectNoEvents()
            }.also {
                assertThat(exception).isNotNull()
            }


    private fun passCommandToAggregate(command: AggregateCommandType) {
        aggregate.handle(command)
    }

}

infix fun <AggregateIdType : AggregateId,
        AggregateCommandType : DomainCommand<AggregateIdType>,
        AggregateEventType : DomainEvent<AggregateIdType>,
        AggregateRootType : EventSourcedAggregateRoot<AggregateIdType, AggregateCommandType, AggregateEventType, AggregateRootType>>
        Any.givenAggregate(block: () -> EventSourcedAggregateRoot<AggregateIdType, AggregateCommandType, AggregateEventType, AggregateRootType>):
        EventSourcedAggregateTestFixture<AggregateIdType, AggregateCommandType, AggregateEventType, AggregateRootType> {
    val aggregate = block.invoke()
    if (aggregate.changes.isNotEmpty()) {
        throw IllegalStateException("Aggregate is in intermediate state! No changes expected!")
    }
    return EventSourcedAggregateTestFixture(aggregate)
}

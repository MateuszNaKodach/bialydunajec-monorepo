package org.bialydunajec.eventsourcing.domain

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import kotlin.reflect.KClass

/*
TODO: Change to Immutable
 */
class EventSourcedAggregateTestFixture<AggregateIdType : AggregateId,
        AggregateCommandType : DomainCommand<AggregateIdType>,
        AggregateEventType : DomainEvent<AggregateIdType>,
        AggregateRootType : EventSourcedAggregateRoot<AggregateIdType, AggregateCommandType, AggregateEventType, AggregateRootType>>(
        private var aggregate: EventSourcedAggregateRoot<AggregateIdType, AggregateCommandType, AggregateEventType, AggregateRootType>
) {

    fun withNoPriorActivity() = EventSourcedTestFixtureWhen(aggregate)

    //TODO: Events needs to be clear after command handle
    /*
    infix fun withPriorCommands(block: () -> List<AggregateCommandType>) =
            apply { block.invoke().forEach { passCommandToAggregate(it) } }
                    .let {
                        EventSourcedTestFixtureWhen(aggregate)
                    }

    infix fun withPriorCommand(block: () -> AggregateCommandType) =
            apply { passCommandToAggregate(block.invoke()) }
                    .let {
                        EventSourcedTestFixtureWhen(aggregate)
                    }
*/
    infix fun withPriorEvent(block: () -> AggregateEventType) =
            apply { passEventToAggregate(block.invoke()) }
                    .let {
                        EventSourcedTestFixtureWhen(aggregate)
                    }

    infix fun withPriorEvents(block: () -> List<AggregateEventType>) =
            apply { block.invoke().forEach { passEventToAggregate(it) } }
                    .let {
                        EventSourcedTestFixtureWhen(aggregate)
                    }


    private fun passCommandToAggregate(command: AggregateCommandType) {
        aggregate = aggregate.handle(command)
    }

    private fun passEventToAggregate(event: AggregateEventType) {
        aggregate = aggregate.replayEvent(event)
    }

}

class EventSourcedTestFixtureWhen<AggregateIdType : AggregateId,
        AggregateCommandType : DomainCommand<AggregateIdType>,
        AggregateEventType : DomainEvent<AggregateIdType>,
        AggregateRootType : EventSourcedAggregateRoot<AggregateIdType, AggregateCommandType, AggregateEventType, AggregateRootType>>(
        private var aggregate: EventSourcedAggregateRoot<AggregateIdType, AggregateCommandType, AggregateEventType, AggregateRootType>
) {

    private var exception: Exception? = null

    infix fun whenCommand(block: () -> AggregateCommandType) =
            apply { passCommandToAggregate(block.invoke()) }
                    .let { EventSourcedTestFixtureExpect(aggregate, exception) }

    private fun passCommandToAggregate(command: AggregateCommandType) {
        try {
            aggregate = aggregate.handle(command)
        } catch (e: Exception) {
            exception = e
        }
    }

}

class EventSourcedTestFixtureExpect<AggregateIdType : AggregateId,
        AggregateCommandType : DomainCommand<AggregateIdType>,
        AggregateEventType : DomainEvent<AggregateIdType>,
        AggregateRootType : EventSourcedAggregateRoot<AggregateIdType, AggregateCommandType, AggregateEventType, AggregateRootType>>(
        private val aggregate: EventSourcedAggregateRoot<AggregateIdType, AggregateCommandType, AggregateEventType, AggregateRootType>,
        private val exception: Exception?
) {

    infix fun <T: AggregateEventType> thenExpectEventWithType(block: () -> KClass<T>) =
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
    if(aggregate.changes.isNotEmpty()){
        throw IllegalStateException("Aggregate is in intermediate state! No changes expected!")
    }
    return EventSourcedAggregateTestFixture(aggregate)
}

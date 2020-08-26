package org.bialydunajec.ddd.domain.base.event

import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.AggregateId
import org.junit.jupiter.api.Test

class InMemoryDomainEventsRecorderSpec {

    @Test
    fun `When No published events | Then should not record any events | And nothing published`() {
        val domainEventBus: DomainEventBus = anDomainEventBus()
        val eventsRecorder = InMemoryDomainEventsRecorder(domainEventBus)

        assertThat(eventsRecorder.recorded).isEmpty()
        verify { domainEventBus wasNot called }
    }

    @Test
    fun `When Published one event | Then the event should be recorded | And published`() {
        val event = anEvent()
        val domainEventBus: DomainEventBus = anDomainEventBus()
        val eventsRecorder = InMemoryDomainEventsRecorder(domainEventBus)

        eventsRecorder.publish(event)

        assertThat(eventsRecorder.recorded).contains(event)
        verify(exactly = 1) { domainEventBus.publish(event) }
    }

    @Test
    fun `When Published many events | Then those event should be recorded in order | And published`() {
        val events = listOf(anEvent(), anEvent(), anEvent())
        val domainEventBus: DomainEventBus = anDomainEventBus()
        val eventsRecorder = InMemoryDomainEventsRecorder(domainEventBus)

        eventsRecorder.publishAll(events)

        assertThat(eventsRecorder.recorded).containsExactlyElementsOf(events)
        verify(exactly = 1) { domainEventBus.publishAll(events) }
    }

}

private fun anEvent() = SampleAggregateEvent.SampleEvent(AggregateId(), "sample-value")

private fun anDomainEventBus(): DomainEventBus {
    return mockk {
        every { publish(any()) } just Runs
        every { publishAll(any()) } just Runs
    }
}

sealed class SampleAggregateEvent(override val aggregateId: AggregateId) : DomainEvent<AggregateId> {
    class SampleEvent(
            aggregateId: AggregateId,
            val eventData1: String
    ) : SampleAggregateEvent(aggregateId) {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            if (!super.equals(other)) return false

            other as SampleEvent

            if (eventData1 != other.eventData1) return false

            return true
        }

        override fun hashCode(): Int {
            var result = super.hashCode()
            result = 31 * result + eventData1.hashCode()
            return result
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SampleAggregateEvent

        if (aggregateId != other.aggregateId) return false

        return true
    }

    override fun hashCode(): Int {
        return aggregateId.hashCode()
    }


}

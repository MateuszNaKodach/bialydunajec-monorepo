package org.bialydunajec.ddd.domain.aggregate

import org.bialydunajec.ddd.domain.event.DomainEvent
import org.bialydunajec.ddd.domain.valueobject.AggregateId
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.AfterDomainEventPublication
import org.springframework.data.domain.DomainEvents
import java.util.ArrayList
import java.util.Collections
import javax.persistence.EmbeddedId

/**
 * Aggregate Root base class based in the Spring Data one {@link org.springframework.data.domain.AbstractAggregateRoot}.
 */
abstract class AggregateRoot<AggregateIdType : AggregateId, EventType : DomainEvent<AggregateIdType>>(
        @EmbeddedId
        private val aggregateId: AggregateIdType
) {

    fun getAggregateId() = aggregateId

    @Transient
    private val domainEvents = ArrayList<EventType>()

    /**
     * Registers the given event object for publication on a call to a Spring Data repository's save methods.
     */
    protected fun registerEvent(event: EventType): EventType {
        this.domainEvents.add(event)
        return event
    }

    /**
     * All domain events currently captured by the aggregate.
     */
    @DomainEvents
    fun domainEvents(): Collection<EventType> {
        return Collections.unmodifiableList<EventType>(domainEvents)
    }

    /**
     * Clears all domain events currently held. Usually invoked by the infrastructure in place in Spring Data
     * repositories.
     */
    @AfterDomainEventPublication
    protected fun clearDomainEvents() {
        this.domainEvents.clear()
    }

}
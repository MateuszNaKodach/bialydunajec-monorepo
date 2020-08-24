package org.bialydunajec.ddd.domain.base.event

/**
 * Domain event bus, which recording published events
 */
class InMemoryDomainEventsRecorder(private val domainEventBus: DomainEventBus?) : DomainEventBus {

    private val _recorded: MutableList<DomainEvent<*>> = mutableListOf()

    val recorded: List<DomainEvent<*>>
        get() = _recorded

    override fun publish(domainEvent: DomainEvent<*>) {
        domainEventBus?.publish(domainEvent)
        _recorded += domainEvent
    }

    override fun publishAll(domainEvents: Collection<DomainEvent<*>>) {
        domainEventBus?.publishAll(domainEvents)
        _recorded += domainEvents
    }

    fun clearRecordedEvents(){
        this._recorded.clear()
    }
}

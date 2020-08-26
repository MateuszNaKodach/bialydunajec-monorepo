package org.bialydunajec.ddd.domain.base.event

/**
 * Domain event bus, which recording published events
 */
class InMemoryDomainEventsRecorder(private val domainEventBus: DomainEventBus?) : DomainEventBus, DomainEventsRecorder {

    private val _recorded: MutableList<DomainEvent<*>> = mutableListOf()
    private var recording = true

    override val recorded: List<DomainEvent<*>>
        get() = _recorded

    override fun publish(domainEvent: DomainEvent<*>) {
        domainEventBus?.publish(domainEvent)
        if(recording){
            _recorded += domainEvent
        }
    }

    override fun publishAll(domainEvents: Collection<DomainEvent<*>>) {
        domainEventBus?.publishAll(domainEvents)
        if(recording){
            _recorded += domainEvents
        }
    }

    override fun clearRecordedEvents(){
        this._recorded.clear()
    }

    override fun withoutRecording(block: () -> Unit){
        recording = false
        block()
        recording = true
    }
}

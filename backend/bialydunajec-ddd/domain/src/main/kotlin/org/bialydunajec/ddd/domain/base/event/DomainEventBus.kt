package org.bialydunajec.ddd.domain.base.event

interface DomainEventBus {
    fun publish(domainEvent: DomainEvent<*>)
    fun publishAll(domainEvents: Collection<DomainEvent<*>>) {
        domainEvents.forEach(this::publish)
    }
}

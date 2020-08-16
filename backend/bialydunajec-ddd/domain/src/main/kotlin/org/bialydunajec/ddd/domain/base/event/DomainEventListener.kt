package org.bialydunajec.ddd.domain.base.event

interface DomainEventListener<EventType : DomainEvent<*>> {

    fun handleDomainEvent(domainEvent: EventType)
}
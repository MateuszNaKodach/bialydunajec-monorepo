package org.bialydunajec.ddd.infrastructure.base.event

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.ddd.domain.base.event.DomainEventBus
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationEventPublisherAware
import org.springframework.stereotype.Component

class SpringDomainEventBus(private val applicationEventPublisher: ApplicationEventPublisher) : DomainEventBus {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun publish(domainEvent: DomainEvent<*>) {
        applicationEventPublisher.publishEvent(domainEvent)
        log.debug("Domain Event published by SpringDomainEventBus: $domainEvent")
    }
}

package org.bialydunajec.ddd.infrastructure.base.event

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.ddd.domain.base.event.DomainEventBus
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationEventPublisherAware
import org.springframework.stereotype.Component

@Component
class SpringDomainEventBus
    : DomainEventBus, ApplicationEventPublisherAware {

    private val log = LoggerFactory.getLogger(this.javaClass)

    lateinit var eventPublisher: ApplicationEventPublisher

    override fun setApplicationEventPublisher(applicationEventPublisher: ApplicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher
    }

    override fun publish(domainEvent: DomainEvent<*>) {
        eventPublisher.publishEvent(domainEvent)
        log.debug("Domain Event published by SpringDomainEventBus: $domainEvent")
    }
}
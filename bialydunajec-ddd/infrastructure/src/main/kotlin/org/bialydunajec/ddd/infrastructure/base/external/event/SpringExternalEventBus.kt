package org.bialydunajec.ddd.infrastructure.base.external.event

import org.bialydunajec.ddd.application.base.external.event.ExternalEvent
import org.bialydunajec.ddd.application.base.external.event.ExternalEventBus
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationEventPublisherAware
import org.springframework.stereotype.Component

@Component
class SpringExternalEventBus
    : ExternalEventBus, ApplicationEventPublisherAware {

    private val log = LoggerFactory.getLogger(this.javaClass)

    lateinit var eventPublisher: ApplicationEventPublisher

    override fun setApplicationEventPublisher(applicationEventPublisher: ApplicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher
    }

    override fun send(event: ExternalEvent<*>) {
        eventPublisher.publishEvent(event)
        log.debug("External event send by SpringExternalEventBus: $event")
    }
}
package org.bialydunajec.ddd.infrastructure.base.external.event

import org.bialydunajec.ddd.application.base.external.event.ExternalEvent
import org.bialydunajec.ddd.application.base.external.event.ExternalEventPublisher
import org.bialydunajec.ddd.application.base.external.event.ExternalEventSubscriber
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.bialydunajec.rxbus.api.RxEventBus


@Component
class RxExternalEventBus
    : ExternalEventPublisher, ExternalEventSubscriber {

    private val log = LoggerFactory.getLogger(this.javaClass)

    private val eventPublisher = RxEventBus.default()

    override fun send(event: ExternalEvent<*>) {
        eventPublisher.publishEvent(event)
        log.debug("External event publisher by RxExternalEventBus: $event")
    }

    override fun <EventType : ExternalEvent<*>> subscribe(eventType: Class<EventType>, consumer: (EventType) -> Unit) {
        eventPublisher.subscribeEvent(eventType){
            log.debug("External event consumed by RxExternalEventBus: $it")
            consumer(it)
        }
    }
}

package org.bialydunajec.ddd.infrastructure.base.external.event

import org.bialydunajec.ddd.application.base.external.event.ExternalEvent
import org.bialydunajec.ddd.application.base.external.event.ExternalEventPublisher
import org.bialydunajec.ddd.application.base.external.event.ExternalEventSubscriber
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.bialydunajec.rxbus.api.RxEventBus
import org.springframework.context.annotation.Primary
import kotlin.reflect.KClass


@Primary
@Component
class RxExternalEventBus
    : ExternalEventPublisher, ExternalEventSubscriber {

    private val log = LoggerFactory.getLogger(this.javaClass)

    private val eventPublisher = RxEventBus.default()

    override fun send(event: ExternalEvent<*>) {
        eventPublisher.publishEvent(event)
        log.debug("External event published by RxExternalEventBus: $event")
    }

    override fun <PayloadType : Any> subscribe(payloadClass: KClass<PayloadType>, consumer: (ExternalEvent<PayloadType>) -> Unit) =
            subscribe(payloadClass.java, consumer)

    override fun <PayloadType : Any> subscribe(payloadClass: Class<PayloadType>, consumer: (ExternalEvent<PayloadType>) -> Unit) {
        eventPublisher.subscribeEvent<ExternalEvent<PayloadType>> {
            if (it.getPayloadClass() == payloadClass) {
                consumer(it)
                log.debug("External event consumed by RxExternalEventBus: $it")
            }
        }
    }

}

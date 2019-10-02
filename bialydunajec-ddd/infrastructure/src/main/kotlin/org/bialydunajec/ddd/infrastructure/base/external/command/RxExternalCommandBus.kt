package org.bialydunajec.ddd.infrastructure.base.external.command

import org.bialydunajec.ddd.application.base.external.command.ExternalCommand
import org.bialydunajec.ddd.application.base.external.command.ExternalCommandBus
import org.bialydunajec.ddd.application.base.external.command.ExternalCommandSubscriber
import org.bialydunajec.rxbus.api.RxEventBus
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import kotlin.reflect.KClass


@Primary
@Component
class RxExternalCommandBus
    : ExternalCommandBus, ExternalCommandSubscriber {

    private val log = LoggerFactory.getLogger(this.javaClass)

    private val commandBus = RxEventBus.default()

    override fun send(event: ExternalCommand<*>) {
        commandBus.publishEvent(event)
        log.debug("External command published by RxExternalCommandBus: $event")
    }

    override fun <PayloadType : Any> subscribe(
        payloadClass: KClass<PayloadType>,
        consumer: (ExternalCommand<PayloadType>) -> Unit
    ) =
        subscribe(payloadClass.java, consumer)

    override fun <PayloadType : Any> subscribe(
        payloadClass: Class<PayloadType>,
        consumer: (ExternalCommand<PayloadType>) -> Unit
    ) {
        commandBus.subscribeEvent<ExternalCommand<PayloadType>> {
            if (it.getPayloadClass() == payloadClass) {
                consumer(it)
                log.debug("External command consumed by RxExternalCommandBus: $it")
            }
        }
    }

    override fun <PayloadType : Any> subscribePayload(
        payloadClass: KClass<PayloadType>,
        consumer: (PayloadType) -> Unit
    ) {
        commandBus.subscribeEvent<ExternalCommand<PayloadType>> {
            if (it.getPayloadClass() == payloadClass) {
                consumer(it.payload)
                log.debug("External command payload consumed by RxExternalCommandBus: $it")
            }
        }
    }

}

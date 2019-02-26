package org.bialydunajec.ddd.application.base.external.event

interface ExternalEventSubscriber {
    fun <PayloadType : Any> subscribe(payloadClass: Class<PayloadType>, consumer: (ExternalEvent<PayloadType>)->Unit)
}

package org.bialydunajec.ddd.application.base.external.event

interface ExternalEventSubscriber {
    fun <PayloadType : Any> subscribe(consumer: (ExternalEvent<PayloadType>)->Unit)
}

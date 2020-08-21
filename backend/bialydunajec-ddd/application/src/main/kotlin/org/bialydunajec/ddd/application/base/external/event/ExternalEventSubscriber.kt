package org.bialydunajec.ddd.application.base.external.event

import kotlin.reflect.KClass

interface ExternalEventSubscriber {
    fun <PayloadType : Any> subscribe(payloadClass: KClass<PayloadType>, consumer: (ExternalEvent<PayloadType>) -> Unit)
    fun <PayloadType : Any> subscribe(payloadClass: Class<PayloadType>, consumer: (ExternalEvent<PayloadType>)->Unit)
}

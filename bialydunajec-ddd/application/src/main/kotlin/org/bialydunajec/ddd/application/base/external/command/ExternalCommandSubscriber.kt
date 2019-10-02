package org.bialydunajec.ddd.application.base.external.command

import kotlin.reflect.KClass

interface ExternalCommandSubscriber {
    fun <PayloadType : Any> subscribe(payloadClass: KClass<PayloadType>, consumer: (ExternalCommand<PayloadType>) -> Unit)
    fun <PayloadType : Any> subscribe(payloadClass: Class<PayloadType>, consumer: (ExternalCommand<PayloadType>)->Unit)
}

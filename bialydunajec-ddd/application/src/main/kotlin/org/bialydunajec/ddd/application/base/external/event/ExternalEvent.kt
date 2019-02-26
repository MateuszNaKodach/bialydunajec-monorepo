package org.bialydunajec.ddd.application.base.external.event

import org.bialydunajec.ddd.application.base.external.ExternalMessage
import org.springframework.core.GenericTypeResolver
import java.time.Instant
import kotlin.reflect.KClass

class ExternalEvent<PayloadType : Any>(
        payload: PayloadType,
        val eventOccurredAt: Instant = Instant.now(),
        val eventType: String = toEventName(payload::class.simpleName)) : ExternalMessage<PayloadType>(payload) {

    companion object {
        private fun toEventName(text: String?) =
                text
                        ?.foldIndexed("") { i, acc, c -> acc + if (i == 0) c else (if (c.isUpperCase()) "_$c" else c) }
                        ?.toUpperCase()
                        ?: "UNKNOWN"
    }

    fun getPayloadClass() = payload::class.java

    override fun toString(): String {
        return "ExternalEvent(eventType='$eventType', eventOccurredAt=$eventOccurredAt, payload=$payload)"
    }

}

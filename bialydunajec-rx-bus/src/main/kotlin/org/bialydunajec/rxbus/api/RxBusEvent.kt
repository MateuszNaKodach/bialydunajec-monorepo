package org.bialydunajec.rxbus.api

import java.time.Instant

class RxBusEvent<PayloadType : Any>(
        payload: PayloadType,
        val eventOccurredAt: Instant = Instant.now(),
        val eventType: String = toEventName(payload::class.simpleName)) : RxBusMessage<PayloadType>(payload) {

    companion object {
        private fun toEventName(text: String?) =
                text
                        ?.foldIndexed("") { i, acc, c -> acc + if (i == 0) c else (if (c.isUpperCase()) "_$c" else c) }
                        ?.toUpperCase()
                        ?: "UNKNOWN"
    }

}

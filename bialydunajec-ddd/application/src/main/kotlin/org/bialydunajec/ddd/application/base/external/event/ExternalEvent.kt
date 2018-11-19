package org.bialydunajec.ddd.application.base.external.event

import org.bialydunajec.ddd.application.base.external.ExternalMessage

class ExternalEvent<PayloadType : Any>(payload: PayloadType, val eventType: String = toEventName(payload::class.simpleName)) : ExternalMessage<PayloadType>(payload) {

    companion object {
        private fun toEventName(text: String?) =
                text
                        ?.foldIndexed("") { i, acc, c -> acc + if (i == 0) c else (if (c.isUpperCase()) "_$c" else c) }
                        ?.toUpperCase()
                        ?: "UNKNOWN"
    }


}


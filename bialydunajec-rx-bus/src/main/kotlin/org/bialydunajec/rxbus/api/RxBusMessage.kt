package org.bialydunajec.rxbus.api

import java.time.Instant
import java.util.*

open class RxBusMessage<PayloadType>(val payload: PayloadType, val messageId: String = UUID.randomUUID().toString(), val createdDate: Instant = Instant.now())

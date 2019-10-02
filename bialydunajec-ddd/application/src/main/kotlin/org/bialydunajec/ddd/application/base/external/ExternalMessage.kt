package org.bialydunajec.ddd.application.base.external

import java.time.Instant
import java.util.*

open class ExternalMessage<PayloadType : Any>(
    val payload: PayloadType,
    val messageId: String = UUID.randomUUID().toString(),
    val createdDate: Instant = Instant.now()
){

    fun getPayloadClass() = payload::class.java

}

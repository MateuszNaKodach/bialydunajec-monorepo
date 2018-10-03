package org.bialydunajec.ddd.application.base.external

open class ExternalMessage<PayloadType>(val payload: PayloadType)
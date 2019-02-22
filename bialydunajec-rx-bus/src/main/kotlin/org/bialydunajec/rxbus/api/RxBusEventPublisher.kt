package org.bialydunajec.rxbus.api

import io.reactivex.Completable

interface RxBusEventPublisher {
    fun publishEvent(event:RxBusEvent<*>): Completable
}

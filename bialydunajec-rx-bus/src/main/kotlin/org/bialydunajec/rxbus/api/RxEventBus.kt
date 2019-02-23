package org.bialydunajec.rxbus.api

import org.bialydunajec.rxbus.RxBus

/**
 * Another implementation for inspiration: https://github.com/Dimezis/RxBus/blob/master/bus/src/main/java/com/eightbitlab/rxbus/Bus.kt
 */
class RxEventBus internal constructor(@PublishedApi internal val rxBus: RxBus) {

    companion object {
        fun default() = RxEventBus(RxBus())
    }

    fun publishEvent(message: Any) =
            rxBus.publish(message)

    inline fun <reified MessageType> subscribeEvent(noinline consumer: (MessageType) -> Unit) {
        rxBus.subscribe<MessageType>(consumer)
    }

    fun unsubscribeConsumer(consumer: Any) {
        rxBus.unsubscribe(consumer)
    }
}

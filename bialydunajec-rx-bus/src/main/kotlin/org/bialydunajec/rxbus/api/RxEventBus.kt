package org.bialydunajec.rxbus.api

import org.bialydunajec.rxbus.RxBus

/**
 * Another implementation for inspiration: https://github.com/Dimezis/RxBus/blob/master/bus/src/main/java/com/eightbitlab/rxbus/Bus.kt
 */
class RxEventBus internal constructor(private val rxBus: RxBus) {

    companion object {
        fun default() = RxEventBus(RxBus())
    }

    fun publishEvent(message: Any) =
            rxBus.publish(message)

    fun <MessageType> subscribeEvent(eventType: Class<MessageType>, consumer: (MessageType) -> Unit) {
        rxBus.subscribe(eventType, consumer)
    }

    fun unsubscribeConsumer(consumer: Any) {
        rxBus.unsubscribe(consumer)
    }
}

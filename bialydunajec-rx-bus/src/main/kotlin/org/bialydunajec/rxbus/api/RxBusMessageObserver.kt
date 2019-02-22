package org.bialydunajec.rxbus.api


interface RxBusMessageObserver {
    fun <T : Any> subscribeForEvents(eventCallback: (RxBusEvent<T>) -> Any)
}

package org.bialydunajec.rxbus

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

internal class RxBus {

    private val disposables = mutableMapOf<Any, CompositeDisposable>()
    private val publisher = PublishRelay.create<Any>().toSerialized()

    fun publish(message: Any) =
            publisher.accept(message)

    fun <MessageType> subscribe(messageType: Class<MessageType>, consumer: (MessageType) -> Unit) {
        val observer = publisher.ofType(messageType)
                .retry()
                .observeOn(Schedulers.newThread())
                .subscribe(consumer)
        val disposable = disposables[consumer] ?: CompositeDisposable().apply { disposables[consumer] = this }
        disposable.add(observer)
    }

    fun unsubscribe(consumer: Any) {
        disposables[consumer]?.clear()
        disposables.remove(consumer)
    }

}

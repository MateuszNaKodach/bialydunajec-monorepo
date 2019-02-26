package org.bialydunajec.rxbus

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

@PublishedApi
internal class RxBus {

    val disposables = mutableMapOf<Any, CompositeDisposable>()
    val publisher = PublishRelay.create<Any>().toSerialized()

    fun publish(message: Any) =
            publisher.accept(message)

    inline fun <reified MessageType : Any> subscribe(noinline consumer: (MessageType) -> Unit) {
        val observer = publisher.ofType(MessageType::class.java)
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

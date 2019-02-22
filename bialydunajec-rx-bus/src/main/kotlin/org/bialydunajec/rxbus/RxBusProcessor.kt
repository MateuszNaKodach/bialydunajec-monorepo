package org.bialydunajec.rxbus

import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.bialydunajec.rxbus.api.RxBusEvent
import org.bialydunajec.rxbus.api.RxBusMessageObserver
import org.bialydunajec.rxbus.api.RxBusEventPublisher

internal class RxBusProcessor : RxBusEventPublisher {

    private val subject = PublishSubject.create<Any>().toSerialized()

    override fun publishEvent(event: RxBusEvent<*>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    inline fun <T : Any> subscribeForEvents(eventCallback: (RxBusEvent<T>) -> Any) =
        observe(eventCallback)
    

    inline fun <reified T : Any> observe(eventCallback: (RxBusEvent<T>) -> Any){
        subject.ofType(T::class.java)
    }
}


class ProcessingSerializedQueue<T>(
        private val onProcess: (T) -> Any?) {

    private val subject = PublishSubject.create<T>().toSerialized()

    init {
        subject
                .doOnNext { onProcess(it) }
                .retry()
                .observeOn(Schedulers.single())
                .subscribe()
    }

    fun process(element: T) {
        subject.onNext(element)
    }
}

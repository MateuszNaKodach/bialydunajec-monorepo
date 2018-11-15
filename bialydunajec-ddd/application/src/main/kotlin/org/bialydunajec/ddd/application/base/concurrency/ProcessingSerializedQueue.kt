package org.bialydunajec.ddd.application.base.concurrency

import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class ProcessingSerializedQueue<T>(
        private val onProcess: (T) -> Any?) {

    private val subject = PublishSubject.create<T>().toSerialized()

    init {
        subject
                .retry()
                .observeOn(Schedulers.single())
                .subscribe { onProcess(it) }
    }

    fun process(element: T) {
        subject.onNext(element)
    }
}
package org.bialydunajec.ddd.application.base.external.event

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

abstract class ObservableEventStream {

    protected val subject = PublishSubject.create<ExternalEvent<*>>().toSerialized()

    fun streamingEvents(): Flowable<ExternalEvent<*>> =
            subject.toFlowable(BackpressureStrategy.LATEST)
    //.sample(2, TimeUnit.SECONDS)

    fun updateStreamWith(events: Collection<ExternalEvent<*>>) {
        events.forEach { subject.onNext(it) }
    }

    fun updateStreamWith(event: ExternalEvent<*>) {
        subject.onNext(event)
    }
}
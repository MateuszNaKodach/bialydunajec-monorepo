package org.bialydunajec.email.readmodel

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.bialydunajec.ddd.application.base.external.event.ExternalEvent
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
internal class EmailMessageLogEventStream {

    private val subject = PublishSubject.create<ExternalEvent<*>>().toSerialized()

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
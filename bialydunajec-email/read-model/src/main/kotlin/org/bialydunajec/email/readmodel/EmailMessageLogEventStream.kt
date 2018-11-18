package org.bialydunajec.email.readmodel

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
internal class EmailMessageLogEventStream {

    private val subject = BehaviorSubject.create<Collection<EmailMessage>>().toSerialized()

    fun observeStreamUpdates(): Flowable<Collection<EmailMessage>> =
            subject.toFlowable(BackpressureStrategy.LATEST)
                    .sample(2, TimeUnit.SECONDS)

    fun updateStreamWith(emailMessages: Collection<EmailMessage>) {
        subject.onNext(emailMessages)
    }

}
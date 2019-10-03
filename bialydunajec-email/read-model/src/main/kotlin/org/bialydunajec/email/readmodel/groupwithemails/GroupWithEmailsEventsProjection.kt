package org.bialydunajec.email.readmodel.groupwithemails

import org.bialydunajec.ddd.application.base.external.event.ExternalEvent
import org.bialydunajec.ddd.application.base.external.event.ExternalEventSubscriber
import org.bialydunajec.ddd.application.base.external.event.SerializedExternalEventListener
import org.bialydunajec.email.messages.event.EmailGroupExternalEvent
import org.springframework.stereotype.Component

@Component
internal class GroupWithEmailsEventsProjection(
    private val repository: GroupWithEmailsMongoRepository,
    private val eventStream: GroupWithEmailsEventStream,
    eventSubscriber: ExternalEventSubscriber
) : SerializedExternalEventListener() {

    init {
        eventSubscriber.subscribe(EmailGroupExternalEvent.EmailGroupCreated::class) {
            processingQueue.process(it)
        }
    }

    override fun processExternalEvent(externalEvent: ExternalEvent<*>) {
        val payload = externalEvent.payload
        when (payload) {
            is EmailGroupExternalEvent.EmailGroupCreated -> {

            }
        }

    }

}

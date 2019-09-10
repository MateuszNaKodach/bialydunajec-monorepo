package org.bialydunajec.email.readmodel

import org.bialydunajec.ddd.application.base.external.event.ExternalEvent
import org.bialydunajec.ddd.application.base.external.event.ExternalEventSubscriber
import org.bialydunajec.ddd.application.base.external.event.SerializedExternalEventListener
import org.bialydunajec.email.messages.event.EmailAddressExternalEvent
import org.bialydunajec.email.messages.event.EmailMessageLogExternalEvent
import org.springframework.stereotype.Component

@Component
internal class EmailAddressEventsProjection(
        private val emailAddresRepository: EmailMessageMongoRepository,
        private val emailAddressStatisticsMongoRepository: EmailAddressStatisticsMongoRepository,
        private val emailAddressEventStream: EmailAddressEventStream,
        eventSubscriber: ExternalEventSubscriber
) : SerializedExternalEventListener() {

    init {
        eventSubscriber.subscribe(EmailAddressExternalEvent.EmailAddressCreated::class) {
            processingQueue.process(it)
        }

        eventSubscriber.subscribe(EmailAddressExternalEvent.EmailAddressUpdated::class) {
            processingQueue.process(it)
        }

        eventSubscriber.subscribe(EmailAddressExternalEvent.EmailAddressCatalogizedToEmailGroup::class) {
            processingQueue.process(it)
        }
    }

    override fun processExternalEvent(externalEvent: ExternalEvent<*>) {
        val payload = externalEvent.payload
        when (payload) {
            is EmailAddressExternalEvent.EmailAddressCreated -> {
                //TODO
            }

            is EmailAddressExternalEvent.EmailAddressUpdated -> {
                //TODO
            }

            is EmailAddressExternalEvent.EmailAddressCatalogizedToEmailGroup -> {
                //TODO
            }
        }
    }

}
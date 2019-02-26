package org.bialydunajec.campschedule.presentation.external

import org.bialydunajec.campedition.messages.event.CampEditionExternalEvent
import org.bialydunajec.campschedule.application.external.CampEditionExternalEventProcessor
import org.bialydunajec.ddd.application.base.external.event.ExternalEvent
import org.bialydunajec.ddd.application.base.external.event.ExternalEventSubscriber
import org.bialydunajec.ddd.application.base.external.event.SerializedExternalEventListener
import org.springframework.stereotype.Component

@Component
internal class CampEditionExternalEventListener internal constructor(
        private val campEditionExternalEventProcessor: CampEditionExternalEventProcessor,
        eventSubscriber: ExternalEventSubscriber
) : SerializedExternalEventListener() {

    init {
        eventSubscriber.subscribe(CampEditionExternalEvent.CampEditionCreated::class.java) {
            processingQueue.process(it)
        }
    }

    override fun processExternalEvent(externalEvent: ExternalEvent<*>) {
        val payload = externalEvent.payload
        when (payload) {
            is CampEditionExternalEvent.CampEditionCreated -> campEditionExternalEventProcessor.process(payload)
        }
    }
}

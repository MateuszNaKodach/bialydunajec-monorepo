package org.bialydunajec.registrations.application.external.event

import org.bialydunajec.campedition.messages.event.CampEditionExternalEvent
import org.bialydunajec.ddd.application.base.external.event.ExternalEvent
import org.bialydunajec.ddd.application.base.external.event.ExternalEventListener
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class CampEditionEventListener : ExternalEventListener<CampEditionExternalEvent> {

    @EventListener
    override fun handleExternalEvent(externalEvent: ExternalEvent<CampEditionExternalEvent>) {
        val payload = externalEvent.payload
        when (payload) {
            is CampEditionExternalEvent.CampEditionCreated -> println("EVENT CampEditionExternalEvent.CampEditionCreated")
            is CampEditionExternalEvent.CampEditionDurationUpdated -> println("EVENT CampEditionExternalEvent.CampEditionDurationUpdated")
        }
    }
}
package org.bialydunajec.campschedule.application.external

import org.bialydunajec.campedition.messages.event.CampEditionExternalEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Transactional(propagation = Propagation.REQUIRES_NEW)
@Component
internal class CampEditionExternalEventProcessor {

    fun process(eventPayload: CampEditionExternalEvent.CampEditionCreated) {

    }
}

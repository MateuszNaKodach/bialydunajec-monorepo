package org.bialydunajec.campedition.application.eventlistener

import org.bialydunajec.campedition.domain.campedition.CampEditionEvent
import org.bialydunajec.campedition.messages.event.CampEditionExternalEvent
import org.bialydunajec.ddd.application.base.external.event.ExternalEventBus
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
internal class CampEditionDomainEventsListener(private val externalEventBus: ExternalEventBus) {

    @EventListener
    fun handleDomainEvent(domainEvent: CampEditionEvent.CampEditionCreated) {
        externalEventBus.send(
                CampEditionExternalEvent.CampEditionCreated(
                        campEditionId = domainEvent.aggregateId.toString(),
                        startDate = domainEvent.startDate,
                        endDate = domainEvent.endDate
                )
        )
    }

    @EventListener
    fun handleDomainEvent(domainEvent: CampEditionEvent.CampEditionDurationUpdated) {
        externalEventBus.send(
                CampEditionExternalEvent.CampEditionDurationUpdated(
                        campEditionId = domainEvent.aggregateId.toString(),
                        startDate = domainEvent.startDate,
                        endDate = domainEvent.endDate
                )
        )
    }
}
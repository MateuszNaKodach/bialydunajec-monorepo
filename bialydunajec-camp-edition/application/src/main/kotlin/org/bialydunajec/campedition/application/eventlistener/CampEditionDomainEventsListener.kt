package org.bialydunajec.campedition.application.eventlistener

import org.bialydunajec.campedition.domain.campedition.CampEditionEvent
import org.bialydunajec.campedition.messages.event.CampEditionExternalEvent
import org.bialydunajec.ddd.application.base.external.event.ExternalEventBus
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class CampEditionDomainEventsListener(private val externalEventBus: ExternalEventBus) {

    @Async
    @TransactionalEventListener
    fun handleDomainEvent(domainEvent: CampEditionEvent.CampEditionCreated) {
        externalEventBus.send(
                CampEditionExternalEvent.CampEditionCreated(
                        campEditionId = domainEvent.aggregateId.toString(),
                        startDate = domainEvent.startDate,
                        endDate = domainEvent.endDate
                )
        )
    }

    @Async
    @TransactionalEventListener
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
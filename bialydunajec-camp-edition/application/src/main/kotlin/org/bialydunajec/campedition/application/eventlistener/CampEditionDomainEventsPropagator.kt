package org.bialydunajec.campedition.application.eventlistener

import org.bialydunajec.campedition.infrastructure.persistence.jpa.CampEditionEvent
import org.bialydunajec.campedition.messages.event.CampEditionExternalEvent
import org.bialydunajec.ddd.application.base.external.event.ExternalEventPublisher
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class CampEditionDomainEventsPropagator(private val externalEventBus: ExternalEventPublisher) {

    @Async
    @TransactionalEventListener
    fun handleDomainEvent(domainEvent: CampEditionEvent.CampEditionCreated) {
        externalEventBus.send(
                CampEditionExternalEvent.CampEditionCreated(
                        campEditionId = domainEvent.aggregateId.toString(),
                        startDate = domainEvent.startDate,
                        endDate = domainEvent.endDate,
                        totalPrice = domainEvent.totalPrice.getValue().toDouble(),
                        downPaymentAmount = domainEvent.downPaymentAmount?.getValue()?.toDouble()
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

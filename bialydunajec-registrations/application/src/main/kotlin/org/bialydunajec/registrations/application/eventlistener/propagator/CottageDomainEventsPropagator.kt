package org.bialydunajec.registrations.application.eventlistener.propagator

import org.bialydunajec.ddd.application.base.external.event.ExternalEventPublisher
import org.bialydunajec.registrations.application.dto.toDto
import org.bialydunajec.registrations.domain.cottage.CottageEvents
import org.bialydunajec.registrations.messages.event.CottageExternalEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class CottageDomainEventsPropagator(
        private val externalEventBus: ExternalEventPublisher
) {

    @Async
    @TransactionalEventListener
    fun handleDomainEvent(domainEvent: CottageEvents.CottageCreated) {
        with(domainEvent) {
            externalEventBus.send(
                    CottageExternalEvent.CottageCreated(
                            aggregateId.toString(),
                            snapshot.toDto()
                    )
            )
        }
    }

    @Async
    @TransactionalEventListener
    fun handleDomainEvent(domainEvent: CottageEvents.CottageUpdated) {
        with(domainEvent) {
            externalEventBus.send(
                    CottageExternalEvent.CottageUpdated(
                            aggregateId.toString(),
                            snapshot.toDto()
                    )
            )
        }
    }

   //TODO: Handle deleted cottage

}

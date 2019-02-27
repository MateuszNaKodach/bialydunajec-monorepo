package org.bialydunajec.registrations.application.eventlistener.propagator

import org.bialydunajec.ddd.application.base.external.event.ExternalEventPublisher
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionEvent
import org.bialydunajec.registrations.messages.event.CampRegistrationsEditionExternalEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class CampRegistrationsEditionDomainEventsPropagator(
        private val externalEventBus: ExternalEventPublisher
) {

    @Async
    @TransactionalEventListener
    fun handleDomainEvent(domainEvent: CampRegistrationsEditionEvent.CampRegistrationsCreated) {
        with(domainEvent) {
            externalEventBus.send(
                    CampRegistrationsEditionExternalEvent.CampRegistrationsCreated(
                            domainEvent.campRegistrationsEditionId.toString(),
                            domainEvent.campRegistrationsId.toString()
                    )
            )
        }
    }

}

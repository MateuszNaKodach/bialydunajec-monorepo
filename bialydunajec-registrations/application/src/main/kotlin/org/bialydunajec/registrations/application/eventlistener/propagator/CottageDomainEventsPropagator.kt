package org.bialydunajec.registrations.application.eventlistener.propagator

import org.bialydunajec.ddd.application.base.external.event.ExternalEventBus
import org.bialydunajec.registrations.application.dto.from
import org.bialydunajec.registrations.application.dto.toDto
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionEvent
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantEvent
import org.bialydunajec.registrations.domain.cottage.CottageEvents
import org.bialydunajec.registrations.domain.cottage.CottageRepository
import org.bialydunajec.registrations.dto.CampParticipantDto
import org.bialydunajec.registrations.messages.event.CampParticipantExternalEvent
import org.bialydunajec.registrations.messages.event.CampRegistrationsEditionExternalEvent
import org.bialydunajec.registrations.messages.event.CottageExternalEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class CottageDomainEventsPropagator(
        private val externalEventBus: ExternalEventBus
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

}
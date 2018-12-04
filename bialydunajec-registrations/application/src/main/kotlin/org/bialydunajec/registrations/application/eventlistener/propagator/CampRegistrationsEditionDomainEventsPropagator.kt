package org.bialydunajec.registrations.application.eventlistener.propagator

import org.bialydunajec.ddd.application.base.external.event.ExternalEventBus
import org.bialydunajec.registrations.application.dto.from
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionEvent
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantEvent
import org.bialydunajec.registrations.domain.cottage.CottageRepository
import org.bialydunajec.registrations.dto.CampParticipantDto
import org.bialydunajec.registrations.messages.event.CampParticipantExternalEvent
import org.bialydunajec.registrations.messages.event.CampRegistrationsEditionExternalEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class CampRegistrationsEditionDomainEventsPropagator(
        private val externalEventBus: ExternalEventBus
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
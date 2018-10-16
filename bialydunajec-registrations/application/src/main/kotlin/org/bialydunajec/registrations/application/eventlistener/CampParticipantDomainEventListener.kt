package org.bialydunajec.registrations.application.eventlistener

import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantEvent
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistration
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistrationRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class CampParticipantDomainEventListener(
        private val campParticipantRegistrationRepository: CampParticipantRegistrationRepository
) {

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun handle(event: CampParticipantEvent.Created) {
        campParticipantRegistrationRepository.save(CampParticipantRegistration.createFrom(event))
    }
}
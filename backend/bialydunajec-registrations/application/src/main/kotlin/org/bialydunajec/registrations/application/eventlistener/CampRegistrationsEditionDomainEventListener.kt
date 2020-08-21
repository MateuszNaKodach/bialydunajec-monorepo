package org.bialydunajec.registrations.application.eventlistener

import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionEvent
import org.bialydunajec.registrations.domain.shirt.CampEditionShirt
import org.bialydunajec.registrations.domain.shirt.CampEditionShirtRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class CampRegistrationsEditionDomainEventListener(
        private val campRegistrationsShirtRepository: CampEditionShirtRepository
) {

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun handle(event: CampRegistrationsEditionEvent.CampRegistrationsCreated) {
        campRegistrationsShirtRepository.save(CampEditionShirt.createFrom(event))
    }
}